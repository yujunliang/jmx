package jmx

import akka.actor._

import scala.concurrent.duration._

private[jmx] class Connection(host: Host)
  extends Actor with Stash with ActorLogging with Predef {

  /**
   * The following is an outline of the responsibilities of this actor, it explains what this actor does,
   * so it is concise and short, you can get an idea of what this actor does without going
   * into too much details.
   */
  override def receive = {
    case GetMBean                     => dispatch()
    case jmx: MBean                   => returnToPool(jmx)
    case BrokenBean(jmx)              => recycle(jmx)
    case Failed(h)                    => retry
    case error: Error                 => eventStream publish error
    case DeadLetter(jmx: MBean, _, _) => returnToPool(jmx)
    case d: DeadLetter                => log.info(s"Ignoring DeadLetter letter $d")
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

  val connector       = context.actorOf(Props(new Connector()), name = "connector")

  var idleConnections = Set[MBean]()
  var busyConnections = Set[MBean]()
  var waiting         = Set[Host]()

  /**
   * Subscribe to DeadLetter
   */
  eventStream.subscribe(self, classOf[DeadLetter])

  /**
   * Unsubscribe DeadLetter
   */
  override def postStop() {
    eventStream.unsubscribe(self)
    super.postStop()
  }

  /**
   * Schedule Retry to Connect to JMX
   */
  private[this] def retry = scheduler.scheduleOnce(retryInterval, connector, Connect(host))

  /**
   * Close the current JMX and create a new one.
   * @param jmx the JMX to be closed.
   */
  private def recycle(jmx: MBean) {
    try 
      jmx.close()
    catch {
      case _: Throwable => /* Ignore */
    }
    connect(jmx.host)
  }

  /**
   * Dispatch an existing JMX or create one if it doesn't exist.
   */
  private[this] def dispatch() = idleConnections.lastOption match {
    case Some(jmx) =>
      idleConnections -= jmx
      busyConnections += jmx
      sender ! jmx

    case None =>
      if (busyConnections.size < maxSize) {
        connect(host)
        stash()
      }
  }

  /**
   * Return the jmx back to pool
   * @param jmx JMX
   */
  private[this] def returnToPool(jmx: MBean) = {
    if (idleConnections.size + busyConnections.size <= maxSize) {
      idleConnections += jmx
      busyConnections -= jmx
    } else {
      jmx.close() 
    }
    waiting -= jmx.host
    unstashAll() 
  }

  /**
   * request to connect to a host
   * @param host the host to connect to
   */
  private[this] def connect(host: Host) = if (!waiting.contains(host)) {
    waiting += host
    connector ! Connect(host)
  }

}

/**
 * Request a JMX bean from the pool
 */
case object GetMBean

/**
 * Sent to event stream when JMX error occurs
 */
private[jmx] case class Error(duration: FiniteDuration, e: Exception)

private[jmx] case class BrokenBean(jmx: MBean)



