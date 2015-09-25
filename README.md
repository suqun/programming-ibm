JVM 并发性: 使用 Akka 执行异步操作
============

长期以来，actor 模型 既被用作分析并发程序的理论基础，也被用作实现并发程序的实践方法。actor 模型基于一种通过消息进行通信的简单 actor 实体结构，让您能够轻松地构建应用程序来实现高并发性和可伸缩性。下面我们将介绍 actor 模型并开始在 Scala 或 Java™ 中使用该模型的 Akka 实现

###Actor 模型基础知识###

用于并发计算的 actor 模型基于各种称为 actor 的原语来构建系统。Actor 执行操作来响应称为消息 的输入。这些操作包括更改 actor 自己的内部状态，以及发出其他消息和创建其他 actor。所有消息都是异步交付的，因此将消息发送方与接收方分开。正是由于这种分离，导致 actor 系统具有内在的并发性：可以不受限制地并行执行任何拥有输入消息的 actor。
在 Akka 术语中，actor 看起来就像是某种通过消息进行交互的行为神经束。像真实世界的演员一样，Akka actor 也需要一定程度的隐私。您不能直接将消息发送给 Akka actor。相反，需要将消息发送给等同于邮政信箱的 actor 引用。然后通过该引用将传入的消息路由到 actor 的邮箱，以后再传送给 actor。Akka actor 甚至要求所有传入的消息都是无菌的（或者在 JVM 术语中叫做不可变的），以免受到其他 actor 的污染。

###Akka 高级知识###

Akka 通过一些可提高灵活性的诀窍来实现 actor 模型。一个诀窍是就是完全分布式架构，允许 actor 系统分散在网络中的多个节点上。Akka 还使用一种分层的 actor 排列，其中每个 actor（除了系统根管理程序）有一个父 actor 负责处理子 actor 的任何故障。这些都是 Akka 的重要特性，但也是高级主题，所以这里只是简单介绍一下。
与一些真实世界中演员的需求不同，Akka 中由于某种原因而存在一些看似强制要求的限制。使用 actor 的引用可阻止交换消息以外的任何交互，这些交互可能破坏 actor 模型核心上的解耦本质。Actor 在执行上是单线程的（不超过 1 个线程执行一个特定的 actor 实例），所以邮箱充当着一个缓冲器，在处理消息前会一直保存这些消息。消息的不可变性（由于 JVM 的限制，目前未由 Akka 强制执行，但这是一项既定的要求）意味着根本无需担心可能影响 actor 之间各种共享的数据的同步问题；如果只有共享的数据是不可变的，那么根本不需要同步。

[原文地址](http://www.ibm.com/developerworks/cn/java/j-jvmc5)

The [fifth article in my JVM Concurrency series](http://www.ibm.com/developerworks/library/j-jvmc5/index.html) on IBM
developerWorks, "Acting asynchronously with Akka", gives an introduction to Akka programming using
parallel examples in Scala, generic Java, and Java 8. The article text focuses mostly on the Scala code
for demonstrating Akka features, but you can find the full code in this repository.

The project uses a Maven build, so just do the usual `mvn clean install` to get
everything to a working state. The code is in three separate packages:

1. `com.sosnoski.concur.article5scala`, within the *main/scala* tree.
2. `com.sosnoski.concur.article5java`, within the *main/java* tree.
3. `com.sosnoski.concur.article5java8`, within the *main/java* tree.

The demonstration code for this article doesn't do much, but if you want to try it out you can run
the Scala demonstration code from the command line with
`mvn scala:run -Dlauncher={name}`, where {name} selects the test code:

1. `hello1` - Simple actor hello
2. `hello2` - Stateful actor hello
3. `hello3` - Actor properties and interactions
4. `hello4` - Asking vs telling

You can import the project into ScalaIDE with the standard Maven project import handling, and both Scala
and Java versions can be executed from within the IDE.
