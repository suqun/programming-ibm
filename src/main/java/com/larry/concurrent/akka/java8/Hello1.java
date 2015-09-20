package com.larry.concurrent.akka.java8;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;

/**
 * Created by larry on 9/20/15.
 * Java 8 的 Akka Hello 版本
 *
 * Java 8 代码使用了一个不同的基类 —AbstractActor 代替 UntypedActor—
 * 而且还使用了一种不同的方式来定义消息处理方案。ReceiveBuilder 类允许您使用 lambda 表达式
 * 来定义消息的处理方式，并采用了类似 Scala 的匹配语法。如果您主要在 Scala 中进行开发工作，
 * 此技术可能有助于让 Java Akka 代码看起来更简洁
 */
public class Hello1 {

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("actor-demo-java");
        ActorRef hello = system.actorOf(Props.create(Hello.class));
        hello.tell("Bob", ActorRef.noSender());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        system.shutdown();
    }

    private static class Hello extends AbstractActor {
        public Hello () {
            receive(ReceiveBuilder.
                    match(String.class, s -> {
                        System.out.println("Hello " + s); })
                    .build());
        }
    }
}
