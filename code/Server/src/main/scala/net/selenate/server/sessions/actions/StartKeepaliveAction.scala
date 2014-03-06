package net.selenate.server
package sessions.actions

import net.selenate.common.comms.req.SeReqStartKeepalive
import net.selenate.common.comms.res.SeResStartKeepalive
import org.openqa.selenium.firefox.FirefoxDriver

class StartKeepaliveAction(val d: FirefoxDriver) extends IAction[SeReqStartKeepalive, SeResStartKeepalive] {
  protected val log = Log(classOf[StartKeepaliveAction])

  def act = { arg =>
    new SeResStartKeepalive()
  }
}
