package net.selenate
package server
package sessions
package actions

import common.comms.res._
import common.comms.req._
import org.openqa.selenium.firefox.FirefoxDriver
import scala.collection.JavaConversions._

class NavigateRefreshAction(val d: FirefoxDriver) extends IAction[SeReqNavigateRefresh, SeResNavigateRefresh] {
  protected val log = Log(classOf[NavigateRefreshAction])

  def act = { SeReqNavigateRefresh =>
    d.navigate.refresh;

    new SeResNavigateRefresh()
  }
}
