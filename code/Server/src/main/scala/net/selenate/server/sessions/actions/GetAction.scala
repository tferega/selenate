package net.selenate.server
package sessions.actions

import net.selenate.common.comms.req.SeReqGet
import net.selenate.common.comms.res.SeResGet
import org.openqa.selenium.firefox.FirefoxDriver

class GetAction(val d: FirefoxDriver) extends IAction[SeReqGet, SeResGet] {
  protected val log = Log(classOf[GetAction])

  def act = { arg =>
    d.get(arg.url)
    new SeResGet()
  }
}
