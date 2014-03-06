package net.selenate.server
package sessions.actions

import net.selenate.common.comms.req.SeReqStopKeepalive
import net.selenate.common.comms.res.SeResStopKeepalive
import org.openqa.selenium.firefox.FirefoxDriver

class StopKeepaliveAction(val d: FirefoxDriver) extends IAction[SeReqStopKeepalive, SeResStopKeepalive] {
  protected val log = Log(classOf[StopKeepaliveAction])

  def act = { arg =>
    new SeResStopKeepalive()
  }
}
