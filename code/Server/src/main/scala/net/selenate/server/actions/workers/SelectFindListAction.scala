package net.selenate.server
package actions
package workers

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqSelectFindList
import net.selenate.common.comms.res.SeResSelectFindList

class SelectFindListAction(val sessionID: String, val context: SessionContext, val d: SelenateFirefox)
    extends Action[SeReqSelectFindList, SeResSelectFindList]
    with ActionCommons {
  def doAct = { arg =>
    val foundElementList = inAllWindows { address =>
      val webElementList = findElementList(arg.getSelector)
      webElementList map parseSelectElement(address, arg.getSelector)
    }.force
    val res = foundElementList.flatten
    new SeResSelectFindList(seqToRealJava(res))
  }
}
