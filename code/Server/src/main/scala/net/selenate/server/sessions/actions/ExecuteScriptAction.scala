package net.selenate.server
package sessions.actions

import net.selenate.common.comms.req.SeReqExecuteScript
import net.selenate.common.comms.res.SeResExecuteScript
import org.openqa.selenium.firefox.FirefoxDriver

class ExecuteScriptAction(val d: FirefoxDriver) extends IAction[SeReqExecuteScript, SeResExecuteScript] {
  protected val log = Log(classOf[ExecuteScriptAction])

  def act = { arg =>
    val result = d.executeScript(arg.javascript)
    new SeResExecuteScript(result.toString)
  }
}
