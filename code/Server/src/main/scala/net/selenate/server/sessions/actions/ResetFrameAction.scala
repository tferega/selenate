package net.selenate.server
package sessions.actions

import driver.selenium.SelenateFirefox

import net.selenate.common.comms.req.SeReqResetFrame
import net.selenate.common.comms.res.SeResResetFrame

class ResetFrameAction(val d: SelenateFirefox)
    extends IAction[SeReqResetFrame, SeResResetFrame]
    with ActionCommons {
  protected val log = Log(classOf[ResetFrameAction])

  def act = { arg =>
    d.switchTo.defaultContent

    new SeResResetFrame()
  }
}
