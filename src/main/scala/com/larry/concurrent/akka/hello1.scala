package com.larry.concurrent.akka

import akka.actor._

/**
 * Created by larry on 9/20/15.
 * Simple hello from an actor in Scala
 */
object Hello1 extends App {

  val system = ActorSystem("actor-demo-scala")
  val hello = system.actorOf(Props[Hello])
  hello ! "Bob"
  Thread sleep 1000
  system shutdown

  class Hello extends Actor {
    def receive = {
      case name: String => println(s"Hello $name")
    }
  }
}

/*
Hello Bob

 */

/*
创建一个 actor 系统（ActorSystem(...) 行）。
在系统内创建一个 actor（system.actorOf(...) 行，它为所创建的 actor 返回一个 actor 引用）。
使用 actor 引用向 actor 发送消息（hello !"Bob" 行）。
等待一秒钟，然后关闭 actor 系统（system shutdown 行）。

system.actorOf(Props[Hello]) 调用是创建 actor 实例的推荐方式，它使用了专门用于 Hello actor 类型的配置属性。
对于这个简单的 actor（扮演一个小角色，只有一句台词），没有配置信息，所以 Props 对象没有参数。如果想在您的 actor 上设置某种配置，
可专门为该 actor 定义一个其中包含了所有必要信息的 Props 类。（后面的示例会展示如何操作。）

hello !"Bob" 语句将一条消息（在本例中为字符串 Bob）发送给已创建的 actor。! 运算符是 Akka 中表示将一条消息发送到 actor 的便捷方式，
采用了触发即忘的模式。如果不喜欢这种特殊的运算符风格，可使用 tell() 方法实现相同的功能。

第二段代码是 Hello actor 定义，以 class Hello extends Actor 开头。这个特定的 actor 定义非常简单。
它定义必需的（对于所有 actor）局部函数 receive，该函数实现了传入消息的处理方式。
（receive 是一个局部函数，因为仅为一些输入定义了它 — 在本例中，仅为 String 消息输入定义了该函数。）为这个 actor 所实现的处理方法是，
只要收到一条 String 消息，就使用该消息值打印一条问候语。*/
