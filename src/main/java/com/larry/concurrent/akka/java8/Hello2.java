package com.larry.concurrent.akka.java8;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;

/**
 * Created by larry on 9/20/15.
 * Simple Hello from actor with state
 */
public class Hello2 {
    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("actor-demo-java8");
        ActorRef hello = system.actorOf(Props.create(Hello.class));
        hello.tell(new Greeting("Hello"), ActorRef.noSender());
        hello.tell(new Greet("Bob"), ActorRef.noSender());
        hello.tell(new Greet("Alice"), ActorRef.noSender());
        hello.tell(new Greeting("Hola"), ActorRef.noSender());
        hello.tell(new Greet("Alice"), ActorRef.noSender());
        hello.tell(new Greet("Bob"), ActorRef.noSender());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) { /* ignore */ }
        system.shutdown();
    }

    private static class Greeting {
        public final String greeting;

        public Greeting(String text) {
            greeting = text;
        }
    }

    private static class Greet {
        public final String name;

        public Greet(String text) {
            name = text;
        }
    }

    private static class Hello extends AbstractActor {
        private String greeting;

        public Hello() {
            receive(ReceiveBuilder.
                    match(Greeting.class, g -> {
                        greeting = g.greeting;
                    }).
                    match(Greet.class, g -> {
                        System.out.println(greeting + " " + g.name);
                    }).
                    build());
        }
    }
}

/*
Hello Bob
Hello Alice
Hola Alice
Hola Bob
 */