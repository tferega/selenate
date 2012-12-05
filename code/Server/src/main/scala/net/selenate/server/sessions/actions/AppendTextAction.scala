package net.selenate
package server
package sessions
package actions

import common.comms.res._
import common.comms.req._
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.{ By, WebElement }
import org.openqa.selenium.internal.selenesedriver.SendKeys

class AppendTextAction(val d: FirefoxDriver)
    extends IAction[SeReqAppendText, SeResAppendText]
    with ActionCommons {
  def act = { arg =>
    val e = findElement(arg.method, arg.selector)
    e.sendKeys(arg.text)

    new SeResAppendText()
  }
}