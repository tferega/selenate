package net.selenate.server
package sessions.actions

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqSelectOption
import net.selenate.common.comms.res.SeResSelectOption
import org.openqa.selenium.support.ui.Select
import scala.collection.JavaConversions._

class SelectOptionAction(val d: SelenateFirefox)(implicit context: ActionContext)
    extends IAction[SeReqSelectOption, SeResSelectOption]
    with ActionCommons {
  protected val log = Log(this.getClass)

  def act = { arg =>
    switchToFrame(arg.windowHandle, arg.framePath.map(_.toInt).toIndexedSeq)
    val e = findElement(arg.parentMethod, arg.parentQuery)
    val s = new Select(e)
    selectOption(s, arg.optionMethod, arg.optionQuery)

    new SeResSelectOption()
  }
}
