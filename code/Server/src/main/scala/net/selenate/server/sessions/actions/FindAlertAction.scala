package net.selenate.server
package sessions.actions

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqFindAlert
import net.selenate.common.comms.res.SeResFindAlert
import org.openqa.selenium.NoAlertPresentException

class FindAlertAction(val d: SelenateFirefox)
    extends IAction[SeReqFindAlert, SeResFindAlert]
    with ActionCommons {
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
