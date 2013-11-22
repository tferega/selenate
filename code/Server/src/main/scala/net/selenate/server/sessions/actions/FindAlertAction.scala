package net.selenate
package server
package sessions
package actions

import common.comms.res._
import common.comms.req._
import java.io.IOException

import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.NoAlertPresentException

class FindAlertAction(val d: FirefoxDriver)
    extends IAction[SeReqFindAlert, SeResFindAlert]
    with ActionCommons {

  protected val log = Log(classOf[FindAlertAction])

  def act = { arg =>
    try {
      val alert = d.switchTo.alert
      val text = alert.getText
      alert.dismiss
      new SeResFindAlert(text)
    } catch {
      case e: NoAlertPresentException =>
        new SeResFindAlert(null)
    }
  }
}
