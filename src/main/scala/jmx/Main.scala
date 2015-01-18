package jmx

import akka.actor._

import scala.concurrent.duration._

object Main extends App {

  val system = ActorSystem("JMX")

  sys.addShutdownHook {
    println("\nShutting down ..")
    system.shutdown()
    system.awaitTermination(20 seconds)
  }

  println("jmx.hosts : " + system.settings.config.getStringList("jmx.hosts"))

  system.actorOf(Props(new MemoryPrinter()), name = "memoryChecker")

  system.awaitTermination()
  println("Stopped JMX")

}