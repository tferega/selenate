package net.selenate.server
package sessions.actions

import org.openqa.selenium.remote.RemoteWebDriver
import net.selenate.server.images.SikuliDirect
import net.selenate.common.comms.req.SeReqSikuliInputText
import net.selenate.common.comms.res.SeResSikuliInputText

class SikuliInputTextAction (val d: RemoteWebDriver) extends IAction[SeReqSikuliInputText, SeResSikuliInputText] {

  protected val log = Log(classOf[SikuliInputTextAction])

  def act = { arg =>
    SikuliDirect.inputText(arg.image, arg.text)
    new SeResSikuliInputText()
  }
}
