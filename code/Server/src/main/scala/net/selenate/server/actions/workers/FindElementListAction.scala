package net.selenate.server
package actions
package workers

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqFindElementList
import net.selenate.common.comms.res.SeResFindElementList

class FindElementListAction(val sessionID: String, val d: SelenateFirefox)(implicit context: ActionContext)
    extends Action[SeReqFindElementList, SeResFindElementList]
    with ActionCommons {
  def act = { arg =>
    val resElementList = inAllWindows { address =>
      val webElementList = findElementList(arg.method, arg.query)
      webElementList map parseWebElement(address)
    }

    new SeResFindElementList(seqToRealJava(resElementList.flatten))
  }
}
