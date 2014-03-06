package net.selenate.server
package sessions.actions

import net.selenate.common.comms.req.SeReqSetUseFrames
import net.selenate.common.comms.res.SeResSetUseFrames
import org.openqa.selenium.firefox.FirefoxDriver

class SetUseFramesAction(val d: FirefoxDriver) extends IAction[SeReqSetUseFrames, SeResSetUseFrames] {
  protected val log = Log(classOf[SetUseFramesAction])

  def act = { arg =>
    new SeResSetUseFrames()
  }
}
