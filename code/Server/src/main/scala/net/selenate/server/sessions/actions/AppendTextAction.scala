package net.selenate.server
package sessions.actions

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqAppendText
import net.selenate.common.comms.res.SeResAppendText
import scala.collection.JavaConversions._

class AppendTextAction(val d: SelenateFirefox)(implicit context: ActionContext)
    extends IAction[SeReqAppendText, SeResAppendText]
    with ActionCommons {
  def act = { arg =>
    switchToFrame(arg.windowHandle, arg.framePath.map(_.toInt).toIndexedSeq)
    val e = findElement(arg.method, arg.query)
    e.sendKeys(arg.text)

    new SeResAppendText()
  }
}
