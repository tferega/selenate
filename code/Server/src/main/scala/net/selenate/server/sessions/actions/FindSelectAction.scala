package net.selenate
package server
package sessions
package actions

import common.comms._
import res._
import req._
import java.util.ArrayList
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.remote.RemoteWebElement
import scala.collection.JavaConversions._


class FindSelectAction(val d: FirefoxDriver)
    extends IAction[SeReqFindSelect, SeResFindSelect]
    with ActionCommons {

  def act = { arg =>
    val resElementList: IndexedSeq[Option[SeSelect]] = inAllFrames { framePath =>
      tryo {
        val webElement = findElement(arg.method, arg.query)
        parseSelectElement(framePath)(webElement)
      }
    }

    val e = resElementList.flatten
    if (e.isEmpty) {
      throw new IllegalArgumentException("Element [%s, %s] was not found in any frame!".format(arg.method.toString, arg.query))
    } else {
      new SeResFindSelect(e(0))
    }
  }
}