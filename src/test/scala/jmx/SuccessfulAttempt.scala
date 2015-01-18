package jmx

import org.scalatest._

class SuccessfulAttempt extends WordSpecLike with Matchers {

  "Modifying case class with direct access" must {
    "failed to modify case class" in {
      val m = Memory(100)
      m.used.queue.size should be(0)

      val m2 = m.copy(used = m.used.copy(queue = m.used.queue.enqueue(2)))

      m2.used.queue.size should be(1)
    }
  }

}
