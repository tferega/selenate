package net.selenate.server
package sessions.actions

import extensions.SelenateFirefox
import images.Images

import net.selenate.common.comms.req.SeReqSikuliClick
import net.selenate.common.comms.res.SeResSikuliClick

class SikuliClickAction(val d: SelenateFirefox) extends IAction[SeReqSikuliClick, SeResSikuliClick] {

  protected val log = Log(classOf[SikuliClickAction])

  def act = { arg =>
    Images.clickImage(d.parentNum, arg.image)
    new SeResSikuliClick()
  }
}
