package jmx

import java.io.IOException
import java.lang.management.MemoryUsage
import javax.management.openmbean.CompositeData
import javax.management.remote.{JMXConnector, JMXConnectorFactory, JMXServiceURL}
import javax.management.{MBeanServerConnection, ObjectName}

import akka.actor.{ActorRef, FSM, Stash}
import akka.io.Tcp.Close
import jmx.MBean._

import scala.collection.JavaConversions._
import scala.concurrent.duration._
import scala.util.{Failure, Success, Try}

/**
 * Wrapper class to Java MBeanConnection
 * @param host        JMX Host
 * @param connectionPool  connectionPool ActorRef
 */
class MBean(
  host           : Host,
  connectionPool : ActorRef
  ) extends FSM[State, Data] with Stash {

  /**
   * The following is an outline of the responsibilities of this state machine, it explains what
   * this state machine does, so it is concise and short, you can get an idea of what this
   * State Machine does without going into too much details.
   */
  startWith(Idle, EventsBuffered)

  when(Idle) {
    case Event(Connect, _)         => connect
  }

  when(WaitingForCall, 10 seconds) {
    case Event(HeapMemoryUsage, _) => getMemoryUsage
                                      stay()
    case Event(Close, _)           => connector.close()
                                      stop()
    case Event(StateTimeout, _)    => stay()
    case _                         => stash()
                                      stay()
  }

  whenUnhandled {
    case Event(m, _)               => goto(WaitingForCall)
  }

  initialize()


  /**
   * The following are the details of this FSM, it explains how this actor works, so if
   * you are working on changing the behaviour of this actor, you can dig into the details now,
   * otherwise you can skip reading them.
   */
  var connector  : JMXConnector = _
  var connection : MBeanServerConnection = _

  def connect = {
    try {
      var map = Map[String, Array[String]]()
      val credentials = new Array[String](2)

      map += "jmx.remote.credentials" -> credentials
      connector = JMXConnectorFactory.newJMXConnector(new JMXServiceURL("rmi", "", 0, s"/jndi/rmi://$host/jmxrmi"), map)
      connector.connect()
      connection = connector.getMBeanServerConnection
      goto(WaitingForCall)
    } catch {
      case e: Exception => goto(Idle) using Connect
    }
  }
  /**
   * Get Memory Usage
   * @return  Java MemoryUsage
   */
  def getMemoryUsage = getAttribute(HeapMemoryUsage.name, HeapMemoryUsage.attribute) match {
    case Some(a) => sender ! MemoryData(host, MemoryUsage.from(a))
    case None    => None
  }

  /**
   * Get JMX Attribute, can be MemoryUsage, GarbageCollectionInfo or others
   * @return  CompositeData representing MemoryUsage, GarbageCollectionInfo or others
   */
  def getAttribute(name: ObjectName, attribute: String) : Option[CompositeData] = {
      execute {
        _.getAttribute(name, attribute).asInstanceOf[CompositeData]
      } match {
        case Success(data) => Some(data)
        case Failure(e)    => None
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
      connectionPool ! self
      Success(result)
    } catch {
      case e: IOException =>
        connectionPool ! BrokenBean(self)
        Failure(e)
      case e : Exception =>
        connectionPool ! Error(startTime.duration, e)
        connectionPool ! self
        Failure(e)
    }
  }
}

object MBean {
  /**
   * trait JMX Attribute
   */
  private[jmx] trait JmxAttribute {
    val name: ObjectName
    val attribute: String
  }

  case object HeapMemoryUsage extends JmxAttribute {
    override val name = new ObjectName("java.lang:type=Memory")
    override val attribute = "HeapMemoryUsage"
  }

  sealed trait State
  case object Idle           extends State
  case object WaitingForCall extends State

  sealed trait Data
  case object EventsBuffered extends Data
  case object Connect        extends Data
  /**
   * message to pass around memory date
   * @param h host
   * @param m memory data
   */
  case class MemoryData(h: Host, m: MemoryUsage)

}




