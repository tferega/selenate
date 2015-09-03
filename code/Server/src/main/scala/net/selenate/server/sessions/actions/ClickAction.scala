package net.selenate
package server
package sessions
package actions

import common.comms.res._
import common.comms.req._

import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.{ By, WebElement }

import scala.collection.JavaConversions._

class ClickAction(val d: RemoteWebDriver)(implicit context: ActionContext)
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
