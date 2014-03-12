package net.selenate.server
package sessions.actions

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqClose
import net.selenate.common.comms.res.SeResClose

class CloseAction(val d: SelenateFirefox)
    extends IAction[SeReqClose, SeResClose]
    with ActionCommons {
  protected val log = Log(classOf[CloseAction])

  def act = { arg =>
    switchToWindow(arg.windowHandle)
    d.close

    new SeResClose()
  }
}
