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


class ElementAction(val d: FirefoxDriver)
    extends IAction[SeReqElement, SeResElement]
    with ActionCommons {

  def act = { arg =>
    val resElementList: IndexedSeq[Option[SeResElement]] = inAllFrames { framePath =>
      tryo {
println("FRAME PATH: "+ framePath.mkString(", "))
        val webElement = findElement(arg.method, arg.query)
        parseWebElement(framePath)(webElement)
      }
    }

    val e = resElementList.flatten
    if (e.isEmpty) {
      throw new IllegalArgumentException("Element [%s, %s] was not found in any frame!".format(arg.method.toString, arg.query))
    } else {
      e(0)
    }
  }
}