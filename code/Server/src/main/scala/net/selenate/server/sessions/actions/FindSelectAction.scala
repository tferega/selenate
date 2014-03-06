package net.selenate.server
package sessions.actions

import net.selenate.common.comms.req.SeReqFindSelect
import net.selenate.common.comms.res.SeResFindSelect
import net.selenate.common.comms.SeSelect
import org.openqa.selenium.firefox.FirefoxDriver

class FindSelectAction(val d: FirefoxDriver)(implicit context: ActionContext)
    extends IAction[SeReqFindSelect, SeResFindSelect]
    with ActionCommons {
  protected val log = Log(classOf[FindSelectAction])

  def act = { arg =>
    val resElementList: Stream[Option[SeSelect]] = inAllWindows { address =>
      tryo {
        val webElement = findElement(arg.method, arg.query)
        parseSelectElement(address)(webElement)
      }
    }

    val e = resElementList.flatten
    if (e.isEmpty) {
      throw new IllegalArgumentException("Element [%s, %s] was not found in any frame!".format(arg.method.toString, arg.query))
    } else {
      new SeResFindSelect(e(0))
    }
  }
}
