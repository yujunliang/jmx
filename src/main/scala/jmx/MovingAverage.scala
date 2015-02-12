package jmx

import jmx.MovingAverage._
import monocle.macros.Lenser
import shapeless._

import scala.Long._
import scala.collection.immutable.Queue
import scala.collection.immutable.Queue._

/**
 * For min and max of some data
 * @param min min
 * @param max max
 */
class Range(val min: Long = MaxValue, val max: Long = MinValue) {

  /**
   * Modify the range after compare to the new sample
   * @param data new sample data
   * @return     modified range
   */
  def update(data: Long) = data match {
    case d if d < min => new Range(d, max)
    case d if d > max => new Range(min, d)
    case _            => this
  }

  /**
   * Modify the range after compare to the new sample
   * @return     modified range
   */
  def updatef = { d : Long => if (min > d) new Range(d, max) else if (max < d) new Range(min, d) else this }

  override def toString = s"$min/$max"

  override def equals(other: Any): Boolean = {
    other match {
      case that: Range => that.min == this.min && that.max == this.max
      case _ => false
    }
  }

  override def hashCode() = {
    max.hashCode() * 31 + min.hashCode()
  }
}

/**
 * data structure to calculate moving average.
 * @param queue entry of 10 samples
 */
case class MovingAverage(total: Long = 0L, range : Range = new Range(), queue: Queue[Long] = empty) {

  /**
   * maintaining max number of 10 samples in the queue, keep track of min and max data entered and total
   * number of samples.
   *
   * @param data  sample data
   * @return      Modified moving average
   */
  def useShapeless(data: Long) = movingAverageLenses.modify(this) { case (t, (r, q)) => (t + 1, (r updatef data, enqueue1(data, q))) }

  def useMonocle(data: Long) = _queue.modify({ q => enqueue1(data, q)})(_range.modify({ r => r updatef data})(_total.modify({ t => t + 1})(this)))
  /**
   * maintaining max number of 10 samples in the queue, keep track of min and max data entered and total
   * number of samples.
   *
   * @param data  sample data
   * @return      Modified moving average
   */
  def useCopy(data: Long) = copy(total = total + 1,  range = range updatef data, queue = enqueue1(data, queue))

  /**
   * the average value of this moving average
   * @return  the average value of this moving average
   */
  def average = if (queue.nonEmpty) queue.sum / queue.size else 0L

  /**
   * The difference of the moving average
   * @param that another moving average
   * @return     the difference of this and another moving average
   */
  def -(that: MovingAverage) = this.average - that.average

  override def toString = s"$average in ($range) of $total samples"
}

/**
 * Shapeless lens objects for Memory case class and nested MovingAverage case class
 */
object MovingAverage {

  /**
   * Educational: This method is for education purpose only
   *
   * The parameters of this method can be curried, but IntelliJ will complain about it.
   * @param d  data to be enqueued
   * @param q  queue
   * @tparam A type parameter of the queue
   * @return   modified queue
   * tupled parameters */
  def enqueue1[A](d: A, q: Queue[A]) : Queue[A] = (if (q.size == 10) q.dequeue._2 else q).enqueue(d)

  /** curried parameters */
  def enqueue2[A](d: A)(q: Queue[A]) : Queue[A] =           enqueue1(d,q)

  /** one parameter, one lambda expression */
  def enqueue3[A](d: A)  : Queue[A] => Queue[A] =      q => enqueue1(d,q)

  /** 0 parameter, two lambda expressions */
  def enqueue4[A]  : A =>  Queue[A] => Queue[A] = d => q => enqueue3(d)(q)

  /** 0 parameter, two lambda expressions */
  def enqueue5[A]  : A =>  Queue[A] => Queue[A] = d =>      enqueue3(d)

  /** 0 parameter, two lambda expressions */
  def enqueue6[A]  : A =>  Queue[A] => Queue[A] =           enqueue3


  /**
   * Lens for count property of MovingAverage
   */
  val total = (lens[MovingAverage] >> 'total).asInstanceOf[LongLens[MovingAverage]]

  /**
   * Lens for range property of MovingAverage
   */
  val range = (lens[MovingAverage] >> 'range).asInstanceOf[RangeLens]

  /**
   * Lens for queue property of MovingAverage
   */
  val queue = (lens[MovingAverage] >> 'queue).asInstanceOf[QueueLens]

  /**
   * Composed lens is better if used repeatedly.
   */
  val movingAverageLenses = total ~ (range ~ queue)

  type LongLens[A] = Lens[A, Long]
  type RangeLens   = Lens[MovingAverage, Range]
  type QueueLens   = Lens[MovingAverage, Queue[Long]]

  val lenser = Lenser[MovingAverage]

  val _total = lenser(_.total)
  val _range = lenser(_.range)
  val _queue = lenser(_.queue)
}