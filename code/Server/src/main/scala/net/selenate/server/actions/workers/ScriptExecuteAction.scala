package net.selenate.server
package actions
package workers

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqScriptExecute
import net.selenate.common.comms.res.SeResScriptExecute

class ScriptExecuteAction(val sessionID: String, val context: SessionContext, val d: SelenateFirefox)
    extends Action[SeReqScriptExecute, SeResScriptExecute]
    with ActionCommons {
  def act = { arg =>
    val result = d.executeScript(arg.getJavascript)
    new SeResScriptExecute(result.toString)
  }
}
