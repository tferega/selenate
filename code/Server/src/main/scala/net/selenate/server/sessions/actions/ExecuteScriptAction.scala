package net.selenate
package server
package sessions
package actions

import common.comms.res._
import common.comms.req._

import org.openqa.selenium.remote.RemoteWebDriver

class ExecuteScriptAction(val d: RemoteWebDriver) extends IAction[SeReqExecuteScript, SeResExecuteScript] {

  protected val log = Log(classOf[ExecuteScriptAction])

  def act = { arg =>
    val result = d.executeScript(arg.javascript)
    new SeResExecuteScript(result.toString)
  }
}
