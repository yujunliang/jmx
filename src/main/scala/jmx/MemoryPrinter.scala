package jmx

import java.lang.management.MemoryUsage

import akka.actor.Actor
import akka.pattern.{ask, _}
import jmx.Memory._
import jmx.MovingAverage._
import shapeless._

import scala.concurrent.duration._

/**
 * In charge of getting memory information from JMX, right now only print a simple chart.
 */
class MemoryPrinter extends Actor with ConnectionPool {

  /**
   * The following is an outline of the responsibilities of this actor, it explains what this actor does,
   * so it is concise and short, you can get an idea of what this actor does without going
   * into too much details.
   */
  override def receive = {
    case Read                   => getMemory
    case MemoryData(h, Some(m)) => process(h, m)
    case Print                  => println(mergeMemoryCharts)
  }

  /**
   * The following are the details of this actor, it explains how this actor works, so if
   * you are working on changing the behaviour of this actor, you can dig into the details now,
   * otherwise you can skip reading them.
   */

  /**
   * Subscribe to the messages of the Response type and APIError type
   * if decide to print the statistics, print the header and schedule this actor to
   * print the statistics every two seconds.
   */
  println("Memory:" + ("-" * 9 + "+") * 25)
  scheduler.schedule(2 seconds, 2 seconds, self, Read)
  scheduler.schedule(4 seconds, 2 seconds, self, Print)


  /**
   * get JMX connection and memory usage data and make a map of host to MemoryUsage
   * @return Map of host to MemoryUsage
   */
  def getMemory = pool.par.map { jmx =>
    val future = for {
      bean <- (jmx.pool ? GetMBean).mapTo[MBean]
      memory = bean.getMemoryUsage
    } yield memory

    future.map(m => self ! MemoryData(jmx.host, m)).recover { case _ => Read } pipeTo self
  }

  /**
   * Transform the memory usage from Java class to Scala case class
   */
  var memory = Map[Host,Memory]()

  /**
   * get new usage data and update the memory
   * @return
   */
  def process(h: Host, m: MemoryUsage) = {
    val mem: Memory = memory.get(h) match {
      case Some(mem0) => mem0
      case None       => Memory(m.getInit)
    }
    memory += h -> (mem << m)
    eventStream.publish(RawMemory(m))
  }

  /**
   * Merge the n lines of memory data chart into one line and use number to indicate the data from different server source, if two server have the same data, use _
   * @return one line presentation of memory level for n hosts
   */
  def mergeMemoryCharts = {

    implicit val order = Ordering.String
    val sorted = memory.keySet.map(_.toString).toList.sorted
    val strings = (1 to sorted.size).map { i =>
      memory.get(sorted(i-1)).get.toString.replaceAll("a", "" + i)
    }
    val maxLength = strings.map(_.length).max
    val chars = ("Memory:" + " " * maxLength).toCharArray
    (7 to maxLength).foreach { i =>
      strings.foreach { s =>
        if (s.length >= i + 1 && s.charAt(i) != ' ') {
          if (chars(i) == ' ') {
            chars.update(i, s.charAt(i))
          } else {
            chars.update(i, '_')
          }
        }
      }
    }
    new String(chars)
  }

  /**
   * Internal message to pass around memory date
   * @param h host
   * @param m memory data
   */
  case class MemoryData(h: Host, m: Option[MemoryUsage])

}

/**
 * Memory data
 * @param init      init memory
 * @param used      used memory
 * @param committed committed memory
 * @param max       max memory
 */
case class Memory(
  init     : Long,
  used     : MovingAverage = MovingAverage(),
  committed: MovingAverage = MovingAverage(),
  max      : MovingAverage = MovingAverage()
  ){

  /**
   * scaled value of init memory
   */
  val scaledInit = scale(init)

  /**
   * Format the memory data into this line so it can be used in a chart,
   *
   *   +-->so it can be grepped by this keyword            +--> used memory                          +--> committed memory                +--> max memory
   *   |                                                   |                                         |                                    |
   * Memory:                                               a                                         a                                    a
   *
   * @return  above formatted string
   */
  override def toString = "Memory:" + " " * (scale(used.average - init) - 1) + "a" + " " * (scale(committed - used) - 1) + "a" + " " * (scale(max - committed) - 1) + "a"

  /**
   * Calculate the scale of the memory
   * @param a memory data in a moving average
   * @return  the scale of the data
   */
  def scale(a: Long): Int = (a / 5000000).asInstanceOf[Int]

  /**
   * Use lens to push the new data into the moving average data structure, this method delegate some
   * responsibility to the << method of MovingAverage.
   * @param l    lens
   * @param data new memory data
   * @return     modified memory from this memory data
   */
  def enqueue(l: MovingAverageLens)(data: Long) = l.modify(this) {_ useShapeless data}

  /**
   * Educational: This method is for education purpose only
   *
   * This method is just for comparison purpose, it has the same effect as the enqueue method and it delegate the the << method
   * in object Memory.
   *
   * @param l    lens
   * @param data new memory data
   * @return     modified memory from this memory data
   */
  def enqueue2(l: MovingAverageLens)(data: Long) = Memory.lens1(this)(l)(data)

  /**
   *
   * @param m memory usage
   * @return  modified memory
   */
  def <<(m: MemoryUsage): Memory = Memory.<<(m)(this)

  /**
   * Enqueue the memory usage into memory, this is one way of using lens
   * @param m memory usage
   * @return  modified memory
   */
  def useLens(m: MemoryUsage): Memory = memoryLenses.modify(this) { case (u, (c, x)) => (u useShapeless m.getUsed, (c useShapeless m.getCommitted, x useShapeless m.getMax))}

  /**
   * This method uses copy, enqueue also uses copy.
   * @param m memory usage
   * @return  modified memory
   */
  def useCopy(m: MemoryUsage): Memory = copy(used = used useCopy m.getUsed).copy(committed = committed useCopy m.getCommitted).copy(max = max useCopy m.getMax)

  /**
   * This method uses copy, enqueue also uses copy.
   * @param m memory usage
   * @return  modified memory
   */
  def useCopy1(m: MemoryUsage): Memory = copy(used = used useCopy m.getUsed, committed = committed useCopy m.getCommitted, max = max useCopy m.getMax)
}

object Memory {

  /**
   * Enqueue the memory usage into memory, this is one way of using lens
   * @param m       memory usage
   * @param memory  memory
   * @return        modified memory
   */
  def <<(m: MemoryUsage)(memory: Memory): Memory = memory.enqueue(used)(m.getUsed).enqueue(committed)(m.getCommitted).enqueue(max)(m.getMax)

  /**
   * Educational: These methods are for education purpose only
   *
   * This method is just for comparison purpose, it has the same effect as the other << method.  It assumes there is no <<
   * method in MovingAverage case class so it uses the composed lens function (count ~(range ~ queue)) to push the
   * data onto the queue, update range and increment total. It can use any of the methods in MovingAverage, bounded, bounded1,
   * bounded2 and bounded3. The parameter lists are already curried in bounded and bounded2, but they are not curried inbounded1
   * and bounded3 so you need to call the the curried method to make them currying. bounded3 causes warning on IntelliJ IDE
   * since its parameters are typed. Please compare the following 4 methods.
   * @param m memory
   * @param l lens
   * @param d new memory data
   * @return  modified memory from this memory data
   */


  def lens1(m: Memory)(l: MovingAverageLens)(d: Long) = (movingAverageLenses compose l).modify(m) { case (c, (r, q)) => (c + 1, (r.update(d), enqueue1(d, q))) }

  def lens2(m: Memory)(l: MovingAverageLens)(d: Long) = (movingAverageLenses compose l).modify(m) { case (c, (r, q)) => (c + 1, (r.update(d), enqueue2(d)(q))) }

  def lens3(m: Memory)(l: MovingAverageLens)(d: Long) = (movingAverageLenses compose l).modify(m) { case (c, (r, q)) => (c + 1, (r.update(d), enqueue3(d)(q))) }

  def lens4(m: Memory)(l: MovingAverageLens)(d: Long) = (movingAverageLenses compose l).modify(m) { case (c, (r, q)) => (c + 1, (r.update(d), enqueue4(d)(q))) }

  /**
   * Lens for used property of Memory
   */
  val used      = (lens[Memory] >> 'used     ).asInstanceOf[MovingAverageLens]

  /**
   * Lens for committed property of Memory
   */
  val committed = (lens[Memory] >> 'committed).asInstanceOf[MovingAverageLens]

  /**
   * Lens for max property of Memory
   */
  val max       = (lens[Memory] >> 'max      ).asInstanceOf[MovingAverageLens]

  /**
   * Composed lens is better if used repeatedly.
   */
  val memoryLenses = used ~ (committed ~ max)

  type MovingAverageLens = Lens[Memory, MovingAverage]

}

case class RawMemory(m: MemoryUsage)

/**
 * Request to update data
 */
case object Read

/**
 * Request to print data
 */
case object Print

