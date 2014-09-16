package net.selenate.server
package actions
package workers

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqBrowserQuit
import net.selenate.common.comms.res.SeResBrowserQuit

class BrowserQuitAction(val sessionID: String, val context: SessionContext, val d: SelenateFirefox)
    extends Action[SeReqBrowserQuit, SeResBrowserQuit]
    with ActionCommons {
  def act = { arg =>
    d.quit
    new SeResBrowserQuit()
  }
}
