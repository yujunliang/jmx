package jmx

import akka.actor._
import akka.pattern._
import akka.testkit.{ImplicitSender, TestKit}
import akka.util.Timeout
import org.scalatest._

import scala.concurrent.Await
import scala.concurrent.duration._

class PerformanceSpec
  extends TestKit(ActorSystem("JMX"))
  with WordSpecLike
  with MustMatchers
  with BeforeAndAfterAll
  with ImplicitSender {

  implicit val futureTimeout = Timeout(10.seconds)

  val jmxPool = JmxPool(system, Host("localhost:11111"))

  override def afterAll() {
    TestKit.shutdownActorSystem(system)
  }

  "Memory enqueue" must {

    "compare the performance between copy and lens" in {

      val jmx = Await.result((jmxPool.pool ? GetMBean).mapTo[MBean], 3.seconds)

      val memory = jmx.getMemoryUsage.get

      var memory1: Memory = Memory(memory.getInit)
      val s = StopWatch()
      (1 to 1000000).foreach { i =>
        memory1 = memory1 useCopy1 memory
      }
      println()
      println(s"using copy= ${s.duration} memory=${memory1.used}")

      memory1 = Memory(memory.getInit)
      val stopWatch = StopWatch()
      (1 to 1000000).foreach { i =>
        memory1 = memory1 useLens memory
      }
      println(s"using lens=${stopWatch.duration} memory=${memory1.used}")

    }

  }

}
