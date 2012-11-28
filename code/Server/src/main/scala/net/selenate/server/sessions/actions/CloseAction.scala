package net.selenate.server
package sessions
package actions

import comms.res._
import comms.req._

import org.openqa.selenium.firefox.FirefoxDriver

import scala.collection.JavaConversions._

class CloseAction(val d: FirefoxDriver)
    extends IAction[SeReqClose, SeResClose]
    with ActionCommons {
  def act = { arg =>
    switchToWindow(arg.windowHandle)
    d.close

    new SeResClose()
  }
}