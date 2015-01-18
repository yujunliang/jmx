package jmx

import java.io.IOException
import java.lang.management.MemoryUsage
import javax.management.openmbean.CompositeData
import javax.management.remote.{JMXConnector, JMXConnectorFactory, JMXServiceURL}
import javax.management.{MBeanServerConnection, ObjectName}

import akka.actor.ActorRef
import jmx.JmxAttribute._

import scala.collection.JavaConversions._
import scala.util.{Failure, Success, Try}

/**
 * Wrapper class to Java MBeanConnection
 * @param host            JMX Host
 * @param connection      Java MBeanServerConnection
 * @param connector       Java JMXConnector
 * @param connectionPool  connectionPool ActorRef
 */
case class MBean(
  host           : Host,
  connection     : MBeanServerConnection,
  connector      : JMXConnector,
  connectionPool : ActorRef
  ){

  /**
   * Close the JMXConnector
   */
  def close() {
    connector.close()
  }

  /**
   * Get Memory Usage
   * @return  Java MemoryUsage
   */
  def getMemoryUsage = getAttribute(heapMemoryUsage) match {
    case Some(a) => Some(MemoryUsage.from(a))
    case None    => None
  }

  /**
   * Get JMX Attribute, can be MemoryUsage, GarbageCollectionInfo or others
   * @return  CompositeData representing MemoryUsage, GarbageCollectionInfo or others
   */
  def getAttribute: JmxAttribute => Option[CompositeData] = {
    j =>
      execute {
        _.getAttribute(j.name, j.attribute).asInstanceOf[CompositeData]
      } match {
        case Success(data) => Some(data)
        case Failure(e) => None
      }
  }

  /**
   * Executing the function to get JMX Attributes and perform some house keeping task
   * @param f function to get JMX Attribute
   * @return  Success of CompositeData or a Failure
   */
  def execute(f: MBeanServerConnection => CompositeData): Try[CompositeData] = {
    val startTime = new StopWatch
    try {
      val result = f(connection)
      connectionPool ! this
      Success(result)
    } catch {
      case e: IOException =>
        connectionPool ! BrokenBean(this)
        Failure(e)
      case e : Exception =>
        connectionPool ! Error(startTime.duration, e)
        connectionPool ! this
        Failure(e)
    }
  }
}

/**
 * Companion object of MBean case class
 */
private[jmx] object MBean {

  def apply(hostPort: Host, poolActor: ActorRef): Try[MBean] = Try {
    var map = Map[String, Array[String]]()
    val credentials = new Array[String](2)

    map += "jmx.remote.credentials" -> credentials
    val connector = JMXConnectorFactory.newJMXConnector(new JMXServiceURL("rmi", "", 0, s"/jndi/rmi://$hostPort/jmxrmi"), map)
    connector.connect()

    MBean (
      host           = hostPort,
      connection     = connector.getMBeanServerConnection,
      connector      = connector,
      connectionPool = poolActor
    )
  }

}

/**
 * Case class of JMX Attribute
 * @param name      name
 * @param attribute attribute
 */
private[jmx] case class JmxAttribute(name: ObjectName, attribute: String)

object JmxAttribute  {
  val heapMemoryUsage = JmxAttribute(
    name      = new ObjectName("java.lang:type=Memory"),
    attribute = "HeapMemoryUsage"
  )
}
