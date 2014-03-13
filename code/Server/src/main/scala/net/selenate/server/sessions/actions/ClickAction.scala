package net.selenate.server
package sessions.actions

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqClick
import net.selenate.common.comms.res.SeResClick
import scala.collection.JavaConversions._

class ClickAction(val d: SelenateFirefox)(implicit context: ActionContext)
    extends IAction[SeReqClick, SeResClick]
    with ActionCommons {
  protected val log = Log(classOf[ClickAction])

  def act = { arg =>
    switchToFrame(arg.windowHandle, arg.framePath.map(_.toInt).toIndexedSeq)
    val e = findElement(arg.method, arg.query)
    e.click

    new SeResClick()
  }
}
