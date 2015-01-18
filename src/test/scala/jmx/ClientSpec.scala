package jmx

import akka.actor._
import akka.pattern._
import akka.testkit.TestKit
import akka.util.Timeout
import org.scalatest._

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.util.{Failure, Success, Try}

class ClientSpec
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

  "The JMXClient" must {

    "getMemory" in {

      val jmx = Await.result((jmxPool.pool ? GetMBean).mapTo[MBean], 3.seconds)

      val memory = jmx.getMemoryUsage.get

      info(s"committed=${memory.getCommitted} total=${memory.getMax} used=${memory.getUsed} ${memory.getUsed.toDouble/memory.getCommitted}  ${memory.getUsed.toDouble/memory.getMax}")

      info(memory.toString)
      memory must not be null
      memory.getCommitted must be > 0L

      jmxPool.pool ! jmx
    }

  }


  "The JMXClient" must {

    "not work when trying to get 6 connections" in {

      val result = Await.result((jmxPool.pool ? GetMBean).mapTo[MBean], 3.seconds)

      Try(
        Await.result((jmxPool.pool ? GetMBean).mapTo[MBean], 3.seconds)
      ) match {
        case Success(service) => fail("Not supposed to")
        case Failure(e)       => info(s"yeah")
      }

    }
  }

  "The JMXClient" must {

    "getMemory display" in {

      system.actorOf(Props(new MemoryPrinter()), name="memory" )
      Thread.sleep(20000)

    }

  }
}
