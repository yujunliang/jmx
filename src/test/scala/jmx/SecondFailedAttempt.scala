package jmx

import org.scalatest._

class SecondFailedAttempt extends WordSpecLike with Matchers {

  "Modifying case class with direct access" must {
    "failed to modify case class" in {
      val m = Memory(100)
      m.used.queue.size should be(0)

      m.copy(used = m.used.copy(queue = m.used.queue.enqueue(2)))

      m.used.queue.size should be(1)
    }
  }

}
