package jmx

import org.scalatest._

class FailedAttempt extends WordSpecLike with Matchers {

  "Modifying case class with direct access" must {
    "failed to modify case class" in {
      val m = Memory(100)
      m.used.queue.size should be(0)

      m.used.queue.enqueue(2)

      m.used.queue.size should be(1)
    }
  }

}
