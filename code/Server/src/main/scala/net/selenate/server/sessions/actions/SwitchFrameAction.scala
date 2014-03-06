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

  protected val log = Log(classOf[SwitchFrameAction])

  def act = { arg =>
    if(arg.frame != null) {
      d.switchTo.frame(arg.frame)
    }
    else {
      val webElement = findElement(arg.selector.method, arg.selector.query)
      d.switchTo().frame(webElement);
    }

    new SeResSwitchFrame()
  }
}
