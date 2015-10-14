package net.selenate.server
package actions
package workers

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqSelectFindOptionList
import net.selenate.common.comms.res.SeResSelectFindOptionList

class SelectFindOptionListAction(val sessionID: String, val context: SessionContext, val d: SelenateFirefox)
    extends Action[SeReqSelectFindOptionList, SeResSelectFindOptionList]
    with ActionCommons {
  def doAct = { arg =>
    ???
  }
}
