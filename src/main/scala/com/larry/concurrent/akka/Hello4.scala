package com.larry.concurrent.akka

import scala.concurrent.duration._
import akka.actor._
import akka.util._
import akka.pattern.ask

/**
 * Created by larry on 9/20/15.
 * 询问与告诉
清单 5 中的代码使用标准 tell 操作来发送消息。在 Akka 中，也可使用 ask 消息模式作为一种辅助性操作。ask 操作（由 ? 运算符或使用 ask 函数表示）发送一条包含 Future 的消息作为响应。
在清单 8 中，我们重建了清单 5 中的代码，使用 ask 来代替 tell。
 */
object Hello4 extends App {

  import Greeter._

  val system = ActorSystem("actor-demo-scala")
  val bob = system.actorOf(props("Bob", "Howya doing"))
  val alice = system.actorOf(props("Alice", "Happy to meet you"))
  bob ! Greet(alice)
  alice ! Greet(bob)
  Thread sleep 1000
  system shutdown

  object Greeter {

    case class Greet(peer: ActorRef)

    case object AskName

    def props(name: String, greeting: String) = Props(new Greeter(name, greeting))
  }

  class Greeter(myName: String, greeting: String) extends Actor {

    import Greeter._
    import system.dispatcher

    implicit val timeout = Timeout(5 seconds)

    def receive = {
      case Greet(peer) => {
        val futureName = peer ? AskName
        futureName.foreach { name => println(s"$greeting, $name") }
      }
      case AskName => sender ! myName
    }
  }

}

//在清单 8 的代码中，TellName 消息已被替换为 ask。ask 操作返回的 future 的类型为 Future[Any]，因为编译器对要返回的结果一无所知。当 future 完成时，foreach 使用 import system.dispatcher 语句所定义的隐式调度器来执行 println。如果 future 未完成且在允许的超时（另一个隐式值，在本例中定义为 5 秒）内提供了响应消息，它会完成并抛出超时异常。
//在幕后，ask 模式创建一个特殊的一次性 actor 在消息交换中充当中介。该中介会收到一个 Promise 和要发送的消息，以及目标 actor 引用。它发送消息，然后等待期望的响应消息。收到响应后，它会履行承诺并完成最初的 actor 所使用的 future。
//使用 ask 方法有一些限制。具体来讲，要避免公开 actor 状态（可能导致线程问题），必须确保您未在 future 完成时所执行的代码中使用来自该 actor 的任何可变状态。在实际情况中，为在 actor 之间发送的消息使用 tell 模式通常要更容易。ask 模式更有用的一种情况是，应用程序代码需要从 actor（无论是否具有类型）获取响应时（比如启动 actor 系统和创建初始 actor 的主程序）。

/*
Happy to meet you, Bob
Howya doing, Alice
 */