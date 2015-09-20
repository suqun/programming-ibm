package com.larry.concurrent.akka

import akka.actor.{Actor, ActorSystem, Props}

/**
 * Created by larry on 9/20/15.
 * Polyglot Scala hello
 *
 * actor 知道如何处理两种不同类型的消息，这些消息在清单的开头附近定义：Greeting 消息和 Greet 消息，
 * 每条消息都包装了一个字符串值。修改后的 Hello actor 收到 Greeting 消息时，会将所包装的字符串保存为 greeting 值。
 * 收到 Greet 消息时，则将已保存的 greeting 值与 Greet 字符串组合起来，形成最终的消息。
 */
object Hello2 extends App{
  case class Greeting(greet: String)
  case class Greet(name: String)

  val system = ActorSystem("actor-demo-scala")
  val hello = system.actorOf(Props[Hello],"hello")
  hello ! Greeting("Hello")
  hello ! Greet("Bob")
  hello ! Greet("Alice")
  hello ! Greeting("Hola")
  hello ! Greet("Alice")
  hello ! Greet("Bob")
  Thread.sleep(1000)
  system.shutdown()

  class Hello extends Actor {
    var greeting = ""
    def receive = {
      case Greeting(greet) => greeting = greet
      case Greet(name) => println(s"$greeting $name")
    }
  }
}

/*
Hello Bob
Hello Alice
Hola Alice
Hola Bob
 */