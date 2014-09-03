package net.selenate.server
package actions
package workers

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqGet
import net.selenate.common.comms.res.SeResGet

class GetAction(val sessionID: String, val d: SelenateFirefox)(implicit context: ActionContext)
    extends RetryableAction[SeReqGet, SeResGet]
    with ActionCommons {
  def retryableAct = { arg =>
    d.executeScript("return window.stop();")
    d.get(arg.url)
    new SeResGet()
  }
}
