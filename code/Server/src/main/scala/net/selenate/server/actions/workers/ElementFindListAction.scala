package net.selenate.server
package actions
package workers

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqElementFindList
import net.selenate.common.comms.res.SeResElementFindList

class ElementFindListAction(val sessionID: String, val context: SessionContext, val d: SelenateFirefox)
    extends Action[SeReqElementFindList, SeResElementFindList]
    with ActionCommons {
  def act = { arg =>
    val foundElementList = inAllWindows { address =>
      val webElementList = findElementList(arg.getSelector)
      webElementList map parseWebElement(address)
    }.force
    val res = foundElementList.flatten
    new SeResElementFindList(seqToRealJava(res))
  }
}
