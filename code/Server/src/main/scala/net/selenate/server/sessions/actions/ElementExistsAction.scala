package net.selenate.server
package sessions.actions

import net.selenate.common.comms.req.SeReqElementExists
import net.selenate.common.comms.res.SeResElementExists
import org.openqa.selenium.firefox.FirefoxDriver

class ElementExistsAction(val d: FirefoxDriver)(implicit context: ActionContext)
    extends IAction[SeReqElementExists, SeResElementExists]
    with ActionCommons {
  protected val log = Log(classOf[ElementExistsAction])

  def act = { arg =>
    val resElementList: Stream[Boolean] = inAllWindows { address =>
      tryb {
        findElement(arg.method, arg.query)
      }
    }

    val isFound = resElementList.contains(true)
    new SeResElementExists(isFound)
  }
}
