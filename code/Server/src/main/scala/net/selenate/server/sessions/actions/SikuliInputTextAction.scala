package net.selenate.server
package sessions.actions

import extensions.SelenateFirefox
import images.Images

import net.selenate.common.comms.req.SeReqSikuliInputText
import net.selenate.common.comms.res.SeResSikuliInputText

class SikuliInputTextAction(val d: SelenateFirefox) extends IAction[SeReqSikuliInputText, SeResSikuliInputText] {

  protected val log = Log(classOf[SikuliInputTextAction])

  def act = { arg =>
    Images.inputText(d.parentNum, arg.image, arg.text)
    new SeResSikuliInputText()
  }
}
