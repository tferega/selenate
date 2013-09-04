package net.selenate
package server
package sessions
package actions

import common.comms.res._
import common.comms.req._

import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.{ By, WebElement }

class SwitchFrameAction(val d: FirefoxDriver)
    extends IAction[SeReqSwitchFrame, SeResSwitchFrame]
    with ActionCommons {
  def act = { arg =>
    d.switchTo.frame(arg.frame)

    new SeResSwitchFrame()
  }
}
