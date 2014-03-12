package net.selenate.server
package sessions.actions

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqExecuteScript
import net.selenate.common.comms.res.SeResExecuteScript

class ExecuteScriptAction(val d: SelenateFirefox) extends IAction[SeReqExecuteScript, SeResExecuteScript] {
  protected val log = Log(classOf[ExecuteScriptAction])

  def act = { arg =>
    val result = d.executeScript(arg.javascript)
    new SeResExecuteScript(result.toString)
  }
}
