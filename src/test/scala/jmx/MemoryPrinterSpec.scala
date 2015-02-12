package jmx

import akka.actor._
import akka.testkit.TestKit
import akka.util.Timeout
import org.scalatest._

import scala.concurrent.duration._

class MemoryPrinterSpec
  extends TestKit(ActorSystem("JMX"))
  with WordSpecLike
  with MustMatchers
  with BeforeAndAfterAll {

  implicit val futureTimeout = Timeout(10.seconds)

  override def afterAll() {
    TestKit.shutdownActorSystem(system)
  }

  val jmxPool = JmxPool(
    system  = system,
    host    = Host("localhost:11111")
  )

  "Memory Printer" must {

    "getMemory and print a chart" in {

      system.actorOf(Props(new MemoryPrinter()), name="memory" )
      Thread.sleep(20000)

    }

  }
}