package net.selenate.server
package actions
package workers

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqClose
import net.selenate.common.comms.res.SeResClose

class CloseAction(val sessionID: String, val d: SelenateFirefox)(implicit context: ActionContext)
    extends Action[SeReqClose, SeResClose]
    with ActionCommons {
  def act = { arg =>
    switchToWindow(arg.windowHandle)
    d.close

    new SeResClose()
  }
}
