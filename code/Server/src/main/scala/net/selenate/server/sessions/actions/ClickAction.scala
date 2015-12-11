package net.selenate
package server
package sessions
package actions

import common.comms.res._
import common.comms.req._

import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.{ By, WebElement }

import scala.collection.JavaConversions._

class ClickAction(val d: FirefoxDriver)(implicit context: ActionContext)
    extends RetryableAction[SeReqClick, SeResClick]
    with ActionCommons {

  protected val log = Log(classOf[ClickAction])

  def retryableAct = { arg =>
    switchToFrame(arg.windowHandle, arg.framePath.map(_.toInt).toIndexedSeq)
    val e = findElement(arg.method, arg.query)
    e.click

    new SeResClick()
  }
}
