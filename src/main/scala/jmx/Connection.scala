package jmx

import akka.actor._
import akka.io.Tcp.Close
import jmx.MBean.{Connect, JmxAttribute}

import scala.concurrent.duration._

private[jmx] class Connection(host: Host)
  extends Actor with Stash with ActorLogging with Predef {

  /**
   * The following is an outline of the responsibilities of this actor, it explains what this actor does,
   * so it is concise and short, you can get an idea of what this actor does without going
   * into too much details.
   */
  override def receive = {
    case e: JmxAttribute                 => get(e)
    case jmx: ActorRef                   => returnToPool(jmx)
    case BrokenBean(jmx)                 => recycle(jmx)
    case error: Error                    => eventStream publish error
    case DeadLetter(jmx: ActorRef, _, _) => returnToPool(jmx)
    case d: DeadLetter                   => log.info(s"Ignoring DeadLetter letter $d")
  }

  /**
   * The following are the details of this actor, it explains how this actor works, so if
   * you are working on changing the behaviour of this actor, you can dig into the details now,
   * otherwise you can skip reading them.
   */
  val retryInterval   = config.getInt("jmx.pool.retry-seconds").seconds

  val maxSize         = config.getInt("jmx.pool.size") match {
    case s if s <= 0 => 1
    case s           => s
  }

  var idleConnections = Set[ActorRef]()
  var busyConnections = Set[ActorRef]()
  var waiting         = Set[Host]()

  /**
   * Subscribe to DeadLetter
   */
  eventStream.subscribe(self, classOf[DeadLetter])
  connect()

  /**
   * Unsubscribe DeadLetter
   */
  override def postStop() {
    eventStream.unsubscribe(self)
    super.postStop()
  }

  /**
   * Close the current JMX and create a new one.
   * @param jmx the JMX to be closed.
   */
  private def recycle(jmx: ActorRef) {
    jmx ! Close
    connect()
  }

  /**
   * Dispatch an existing JMX or create one if it doesn't exist.
   */
  private[this] def get(e : JmxAttribute) = idleConnections.lastOption match {
    case Some(jmx) =>
      idleConnections -= jmx
      busyConnections += jmx
      jmx forward e

    case None =>
      if (busyConnections.size < maxSize) {
        connect()
        stash()
      }
  }

  /**
   * Return the jmx back to pool
   * @param jmx JMX
   */
  private[this] def returnToPool(jmx: ActorRef) = {
    if (idleConnections.size + busyConnections.size <= maxSize) {
      idleConnections += jmx
      busyConnections -= jmx
    } else {
      jmx ! Close
    }
    waiting -= host
    unstashAll()
  }

  /**
   * request to connect to a host
   */
  private[this] def connect() = if (!waiting.contains(host)) {
    waiting += host
    val jmx = context.actorOf(Props(new MBean(host, self)))
    jmx  ! Connect
    self ! jmx
  }

}

/**
 * Sent to event stream when JMX error occurs
 */
private[jmx] case class Error(duration: FiniteDuration, e: Exception)

private[jmx] case class BrokenBean(jmx: ActorRef)



