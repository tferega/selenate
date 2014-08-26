package net.selenate.server
package sessions.actions

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqExecuteScript
import net.selenate.common.comms.res.SeResExecuteScript

class ExecuteScriptAction(val sessionID: String, val d: SelenateFirefox)(implicit context: ActionContext)
    extends Action[SeReqExecuteScript, SeResExecuteScript]
    with ActionCommons {
  def act = { arg =>
    val result = d.executeScript(arg.javascript)
    new SeResExecuteScript(result.toString)
  }
}
