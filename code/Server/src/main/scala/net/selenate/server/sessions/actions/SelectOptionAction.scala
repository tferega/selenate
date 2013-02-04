package net.selenate
package server
package sessions
package actions

import common.comms.res._
import common.comms.req._
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.{ By, WebElement }
import org.openqa.selenium.internal.selenesedriver.SendKeys
import scala.collection.JavaConversions._
import org.openqa.selenium.support.ui.Select


class SelectOptionAction(val d: FirefoxDriver)
    extends IAction[SeReqSelectOption, SeResSelectOption]
    with ActionCommons {
  def act = { arg =>
    val fp = arg.framePath.map(_.toInt).toIndexedSeq

    switchToFrame(d.getWindowHandle, fp)
    val e = findElement(arg.parentMethod, arg.parentQuery)
    val s = new Select(e)
    selectOption(s, arg.optionMethod, arg.optionQuery)

    val select = parseSelectElement(fp)(e)
    new SeResSelectOption(select)
  }
}