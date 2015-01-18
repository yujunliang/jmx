package setup

import jmx.Copy
import org.scalatest._

class JMX4Spec extends WordSpecLike with Copy {

  "A while loop doing copy" must {
    "not terminate" in {
      doCopy()
    }
  }
}
