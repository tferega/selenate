package net.selenate
package server
package sessions
package actions

import common.comms.res._
import common.comms.req._
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.{ By, WebElement }
import scala.collection.JavaConversions._
import org.openqa.selenium.support.ui.Select


class SelectOptionAction(val d: FirefoxDriver)(implicit context: ActionContext)
    extends IAction[SeReqSelectOption, SeResSelectOption]
    with ActionCommons {

  protected val log = Log(classOf[SelectOptionAction])

  def act = { arg =>
    switchToFrame(arg.windowHandle, arg.framePath.map(_.toInt).toIndexedSeq)
    val e = findElement(arg.parentMethod, arg.parentQuery)
    val s = new Select(e)
    selectOption(s, arg.optionMethod, arg.optionQuery)

    new SeResSelectOption()
  }
}
