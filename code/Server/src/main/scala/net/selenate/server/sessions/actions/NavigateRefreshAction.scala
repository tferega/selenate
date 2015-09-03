package net.selenate
package server
package sessions
package actions

import common.comms.res._
import common.comms.req._
import org.openqa.selenium.remote.RemoteWebDriver
import scala.collection.JavaConversions._

class NavigateRefreshAction(val d: RemoteWebDriver) extends IAction[SeReqNavigateRefresh, SeResNavigateRefresh] {
  protected val log = Log(classOf[NavigateRefreshAction])

  def act = { SeReqNavigateRefresh =>
    d.navigate.refresh;

    new SeResNavigateRefresh()
  }
}
