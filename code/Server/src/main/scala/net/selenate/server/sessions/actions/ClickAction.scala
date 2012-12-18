package net.selenate
package server
package sessions
package actions

import common.comms.res._
import common.comms.req._

import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.{ By, WebElement }

import scala.collection.JavaConversions._

class ClickAction(val d: FirefoxDriver)
    extends IAction[SeReqClick, SeResClick]
    with ActionCommons {
  def act = { arg =>
    switchToFrame(d.getWindowHandle, arg.framePath.map(_.toInt))
    val e = findElement(arg.method, arg.query)
    e.click

    new SeResClick()
  }
}