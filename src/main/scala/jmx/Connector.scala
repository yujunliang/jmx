package jmx

import akka.actor._

import scala.util.{Failure, Success}

/**
 * Manage the connecting activity to JMX host
 */
private[jmx] class Connector extends Actor with ActorLogging with Predef {

  override def receive = {

    case Connect(host) => MBean(host, context.parent) match {
      case Success(jmx) => sender ! jmx
      case Failure(_)   => sender ! Failed(host)
    }
  }
}

/**
 * Request to connect to a JMX host
 * @param host  host to connect to
 */
case class Connect(host: Host)

/**
 * Signal of the failure in connecting to the host
 * @param host JVM host
 */
case class Failed(host: Host)
