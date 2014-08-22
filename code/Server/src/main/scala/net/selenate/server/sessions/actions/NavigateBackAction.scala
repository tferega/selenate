package net.selenate.server
package sessions.actions

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqNavigateBack
import net.selenate.common.comms.res.SeResNavigateBack

class NavigateBackAction(val d: SelenateFirefox)
    extends IAction[SeReqNavigateBack, SeResNavigateBack]
    with ActionCommons {
  def act = { arg =>
    d.navigate.back;

    new SeResNavigateBack()
  }
}
