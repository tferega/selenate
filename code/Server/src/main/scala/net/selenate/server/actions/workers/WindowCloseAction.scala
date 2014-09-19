package net.selenate.server
package actions
package workers

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqWindowClose
import net.selenate.common.comms.res.SeResWindowClose

class WindowCloseAction(val sessionID: String, val context: SessionContext, val d: SelenateFirefox)
    extends Action[SeReqWindowClose, SeResWindowClose]
    with ActionCommons {
  def doAct = { arg =>
    if (context.useFrames) {
      windowSwitch(arg.getWindowHandle)
    }
    d.close

    new SeResWindowClose()
  }
}
