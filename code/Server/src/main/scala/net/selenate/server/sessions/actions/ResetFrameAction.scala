package net.selenate
package server
package sessions
package actions

import common.comms.res._
import common.comms.req._

import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.{ By, WebElement }

class ResetFrameAction(val d: RemoteWebDriver)
    extends IAction[SeReqResetFrame, SeResResetFrame]
    with ActionCommons {

  protected val log = Log(classOf[ResetFrameAction])

  def act = { arg =>
    d.switchTo.defaultContent

    new SeResResetFrame()
  }
}
