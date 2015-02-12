package jmx

import java.lang.management.MemoryUsage

import akka.actor._
import akka.testkit.TestKit
import org.scalatest._

class PerformanceSpec
  extends TestKit(ActorSystem("JMX"))
  with WordSpecLike
  with MustMatchers
  with BeforeAndAfterAll {

  override def afterAll() {
    TestKit.shutdownActorSystem(system)
  }

  "Memory enqueue" must {

    "compare the performance between copy and lens" in {

      val memory = new MemoryUsage(123,234,345,456)

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
