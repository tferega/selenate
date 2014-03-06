package net.selenate.server
package sessions.actions

import net.selenate.common.comms.req.SeReqFindElementList
import net.selenate.common.comms.res.SeResFindElementList
import org.openqa.selenium.firefox.FirefoxDriver

class FindElementListAction(val d: FirefoxDriver)(implicit context: ActionContext)
    extends IAction[SeReqFindElementList, SeResFindElementList]
    with ActionCommons {
  protected val log = Log(classOf[FindElementListAction])

  def act = { arg =>
    val resElementList = inAllWindows { address =>
      val webElementList = findElementList(arg.method, arg.query)
      webElementList map parseWebElement(address)
    }

    new SeResFindElementList(seqToRealJava(resElementList.flatten))
  }
}
