package net.selenate.server
package sessions.actions

import driver.selenium.SelenateFirefox

import net.selenate.common.comms.req.SeReqSetUseFrames
import net.selenate.common.comms.res.SeResSetUseFrames

class SetUseFramesAction(val d: SelenateFirefox) extends IAction[SeReqSetUseFrames, SeResSetUseFrames] {
  protected val log = Log(classOf[SetUseFramesAction])

  def act = { arg =>
    new SeResSetUseFrames()
  }
}
