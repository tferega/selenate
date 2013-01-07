package net.selenate
package server
package sessions
package actions

import common.comms.res._
import common.comms.req._
import java.util.ArrayList
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.remote.RemoteWebElement
import scala.collection.JavaConversions._


class FindAndClickAction(val d: FirefoxDriver)
    extends IAction[SeReqFindAndClick, SeResFindAndClick]
    with ActionCommons {
  type PathElement = (FramePath, RemoteWebElement)

  def act = { arg =>
    val elementList: IndexedSeq[PathElement] =
      inAllFrames { framePath =>
        tryo {
          (framePath, findElement(arg.method, arg.query))
        }
      }.flatten

    elementList.headOption match {
      case Some((framePath, element)) =>
        switchToFrame(d.getWindowHandle, framePath)
        element.click
        new SeResFindAndClick()
      case None =>
        throw new IllegalArgumentException("Element [%s, %s] was not found in any frame!".format(arg.method.toString, arg.query))
    }
  }
}