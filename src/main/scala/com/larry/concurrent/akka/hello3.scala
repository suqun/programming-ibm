package com.larry.concurrent.akka

import akka.actor.{ActorSystem, Props, ActorRef, Actor}

/**
 * Created by larry on 9/20/15.
 * Actor 属性和交互
 * 真正的 actor 系统会使用多个 actor 来完成工作，它们彼此发送消息来进行交互。
 * 并且常常需要为这些 actor 提供配置信息，以准备履行其具体的职责。
 * 基于 Hello 示例中使用的技术，展示了简化版的 actor 配置和交互。
 */
object hello3 extends App{

  import Greeter._
  val system = ActorSystem("actor-demo-scala")
  val bob = system.actorOf(props("Bob","Howya doing"))
  val alice = system.actorOf(props("Alice","Happy to meet you"))
  bob ! Greet(alice)
  alice ! Greet(bob)
  Thread sleep 1000
  system shutdown

  object Greeter {
    case class Greet(peer: ActorRef)
    case object AskName
    case class TellName(name: String)
    def props(name: String,greeting: String) = Props(new Greeter(name,greeting))
  }

  class Greeter(myName: String,greeting: String) extends Actor {
    import Greeter._
    def receive = {
      case Greet(peer) => peer ! AskName
      case AskName => sender ! TellName(myName)
      case TellName(name) => println(s"$greeting,$name")
    }
  }
}
//在领导角色中包含了一个新的 actor，即 Greeter actor。Greeter 在 Hello2 示例的基础上更进了一步，包含：
//所传递的属性，目的是配置 Greeter 实例
//定义了配置属性和消息的 Scala 配套对象（如果您有 Java 工作背景，可将这个配套对象视为与 actor 类同名的静态 helper 类）
//在 Greeter actor 的实例间发送的消息
//此代码生成的输出很简单：
/*
Howya doing,Alice
Happy to meet you,Bob
 */
//如果尝试运行该代码几次，可能会看到这些行的顺序是相反的。
//这种排序是 Akka actor 系统动态本质的另一个例子，其中处理各个消息时的顺序是不确定的