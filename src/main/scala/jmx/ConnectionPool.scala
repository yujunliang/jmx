package jmx

import akka.actor.{ActorRef, ActorRefFactory, Props}

import scala.collection.JavaConversions._
import scala.language.implicitConversions

/**
 * A trait with access to JMX connection
 */
trait ConnectionPool extends Predef {
  /**
   * jmx connection pool to JVM hosts
   */
  val pool = config.getStringList("jmx.hosts").map(Host(_)).map(JmxPool(system = context.system, _))
}

private[jmx] case class JmxPool(pool: ActorRef, host: Host)

private[jmx] object JmxPool {

  def apply(system: ActorRefFactory, host: Host) = new JmxPool(
    pool = system.actorOf(Props(new Connection(host))),
    host = host
  )
}

private[jmx] case class Host(host: String, port: Int) {
  override def toString = s"$host:$port"
}

private[jmx] object Host {

  /**
   * @param hostPort "host:port"
   * @return         JMX host/port
   */
  @throws[java.lang.NumberFormatException]
  def apply(hostPort: String): Host = {
    val a = hostPort.trim.split(':')
    Host(a(0), a(1).toInt)
  }

  implicit def stringToHost(str: String): Host = Host(str)

}
