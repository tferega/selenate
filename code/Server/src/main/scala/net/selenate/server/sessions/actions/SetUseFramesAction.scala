package net.selenate
package server
package sessions
package actions

import common.comms.res._
import common.comms.req._

import org.openqa.selenium.remote.RemoteWebDriver

class SetUseFramesAction(val d: RemoteWebDriver) extends IAction[SeReqSetUseFrames, SeResSetUseFrames] {

  protected val log = Log(classOf[SetUseFramesAction])

  def act = { arg =>
    new SeResSetUseFrames()
  }
}
