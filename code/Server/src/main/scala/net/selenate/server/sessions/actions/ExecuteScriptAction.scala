package net.selenate
package server
package sessions
package actions

import common.comms.res._
import common.comms.req._

import org.openqa.selenium.firefox.FirefoxDriver

class ExecuteScriptAction(val d: FirefoxDriver) extends IAction[SeReqExecuteScript, SeResExecuteScript] {
  def act = { arg =>
    val result = d.executeScript(arg.javascript)
    new SeResExecuteScript(result.toString)
  }
}