package net.selenate
package server
package sessions
package actions

import common.comms.res._
import common.comms.req._

import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.remote.RemoteWebElement

import scala.collection.JavaConversions._


class ElementListAction(val d: FirefoxDriver)
    extends IAction[SeReqElementList, SeResElementList]
    with ActionCommons {
  def act = { arg =>
    val resElementList = inAllFrames {
      val webElementList = findElementList(arg.method, arg.query)
      webElementList map parseWebElement
    }

    new SeResElementList(seqToRealJava(resElementList.flatten))
  }
}