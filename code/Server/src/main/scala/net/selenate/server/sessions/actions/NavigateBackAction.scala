package net.selenate
package server
package sessions
package actions

import common.comms.res._
import common.comms.req._
import org.openqa.selenium.remote.RemoteWebDriver
import scala.collection.JavaConversions._

class NavigateBackAction(val d: RemoteWebDriver) extends IAction[SeReqNavigateBack, SeResNavigateBack] {
  protected val log = Log(classOf[NavigateBackAction])

  def act = { arg =>
    d.navigate.back;

    new SeResNavigateBack()
  }
}
