package net.selenate.server
package sessions.actions

import driver.selenium.SelenateFirefox

import net.selenate.common.comms.req.SeReqStopKeepalive
import net.selenate.common.comms.res.SeResStopKeepalive

class StopKeepaliveAction(val d: SelenateFirefox) extends IAction[SeReqStopKeepalive, SeResStopKeepalive] {
  protected val log = Log(classOf[StopKeepaliveAction])

  def act = { arg =>
    new SeResStopKeepalive()
  }
}
