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


class AppendTextAction(val d: FirefoxDriver)
    extends IAction[SeReqAppendText, SeResAppendText]
    with ActionCommons {
  def act = { arg =>
    switchToFrame(arg.windowHandle, arg.framePath.map(_.toInt).toIndexedSeq)
    val e = findElement(arg.method, arg.query)
    e.sendKeys(arg.text)

    new SeResAppendText()
  }
}
