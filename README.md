JVM 并发性: 使用 Akka 执行异步操作
============

长期以来，actor 模型 既被用作分析并发程序的理论基础，也被用作实现并发程序的实践方法。actor 模型基于一种通过消息进行通信的简单 actor 实体结构，让您能够轻松地构建应用程序来实现高并发性和可伸缩性。下面我们将介绍 actor 模型并开始在 Scala 或 Java™ 中使用该模型的 Akka 实现

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