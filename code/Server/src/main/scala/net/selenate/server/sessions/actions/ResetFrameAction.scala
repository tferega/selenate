package net.selenate.server
package sessions.actions

import net.selenate.common.comms.req.SeReqResetFrame
import net.selenate.common.comms.res.SeResResetFrame
import org.openqa.selenium.firefox.FirefoxDriver

class ResetFrameAction(val d: FirefoxDriver)
    extends IAction[SeReqResetFrame, SeResResetFrame]
    with ActionCommons {
  protected val log = Log(classOf[ResetFrameAction])

  def act = { arg =>
    d.switchTo.defaultContent

    new SeResResetFrame()
  }
}
