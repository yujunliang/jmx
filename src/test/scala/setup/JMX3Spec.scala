package setup

import jmx.Copy
import org.scalatest._

class JMX3Spec extends WordSpecLike with Copy {

  "A while loop doing copy" must {
    "not terminate" in {
      doCopy()
    }
  }

}
