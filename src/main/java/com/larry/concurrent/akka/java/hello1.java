package com.larry.concurrent.akka.java;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;

/**
 * Created by larry on 9/20/15.
 * Akka Hello 在普通 Java 中的表示
 */
public class hello1 {
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

    private static class Hello extends UntypedActor {
        public void onReceive(Object message) throws Exception {
            if (message instanceof String) {
                System.out.println("Hello " + message);
            }
        }
    }
}

/*
Hello Bob

 */