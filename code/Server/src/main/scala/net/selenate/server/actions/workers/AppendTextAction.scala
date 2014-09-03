package net.selenate.server
package actions
package workers

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqAppendText
import net.selenate.common.comms.res.SeResAppendText
import scala.collection.JavaConversions._

class AppendTextAction(val sessionID: String, val d: SelenateFirefox)(implicit context: ActionContext)
    extends Action[SeReqAppendText, SeResAppendText]
    with ActionCommons {
  def act = { arg =>
    switchToFrame(arg.windowHandle, arg.framePath.map(_.toInt).toIndexedSeq)
    val e = findElement(arg.method, arg.query)
    e.sendKeys(arg.text)

    new SeResAppendText()
  }
}
