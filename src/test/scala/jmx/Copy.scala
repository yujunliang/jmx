package jmx

import org.scalatest.Matchers

trait Copy extends Matchers {

  def doCopy() {
    val m = MovingAverage()
    while (true) {
      (1 to 10000000).foreach { i =>
        val m1 = m.useCopy(i)
        val m2 = m.useMonocle(i)
        val m3 = m.useShapeless(i)
        m1 should be(m2)
        m1 should be(m3)
      }
    }
  }

}
