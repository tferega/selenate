package net.selenate.server
package sessions.actions

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqClearText
import net.selenate.common.comms.res.SeResClearText
import scala.collection.JavaConversions._

class ClearTextAction(val d: SelenateFirefox)(implicit context: ActionContext)
    extends IAction[SeReqClearText, SeResClearText]
    with ActionCommons {
  protected val log = Log(this.getClass)

  def act = { arg =>
    switchToFrame(arg.windowHandle, arg.framePath.map(_.toInt).toIndexedSeq)
    val e = findElement(arg.method, arg.query)
    e.clear

    new SeResClearText()
  }
}
