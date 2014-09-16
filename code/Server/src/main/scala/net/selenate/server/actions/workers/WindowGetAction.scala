package net.selenate.server
package actions
package workers

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqWindowGet
import net.selenate.common.comms.res.SeResWindowGet

class WindowGetAction(val sessionID: String, val context: SessionContext, val d: SelenateFirefox)
    extends RetryableAction[SeReqWindowGet, SeResWindowGet]
    with ActionCommons {
  def retryableAct = { arg =>
    d.executeScript("return window.stop();")
    d.get(arg.getUrl)
    new SeResWindowGet()
  }
}
