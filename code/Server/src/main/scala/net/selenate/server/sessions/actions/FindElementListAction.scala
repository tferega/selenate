package net.selenate.server
package sessions.actions

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqFindElementList
import net.selenate.common.comms.res.SeResFindElementList

class FindElementListAction(val d: SelenateFirefox)(implicit context: ActionContext)
    extends IAction[SeReqFindElementList, SeResFindElementList]
    with ActionCommons {
  protected val log = Log(this.getClass)

  def act = { arg =>
    val resElementList = inAllWindows { address =>
      val webElementList = findElementList(arg.method, arg.query)
      webElementList map parseWebElement(address)
    }

    new SeResFindElementList(seqToRealJava(resElementList.flatten))
  }
}
