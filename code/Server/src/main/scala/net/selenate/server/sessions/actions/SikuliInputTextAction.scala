package net.selenate.server
package sessions.actions

import org.openqa.selenium.firefox.FirefoxDriver
import net.selenate.server.images.SikuliDirect
import net.selenate.common.comms.req.SeReqSikuliInputText
import net.selenate.common.comms.res.SeResSikuliInputText

class SikuliInputTextAction (val d: FirefoxDriver) extends IAction[SeReqSikuliInputText, SeResSikuliInputText] {

  protected val log = Log(classOf[SikuliInputTextAction])

  def act = { arg =>
    SikuliDirect.inputText(arg.image, arg.text)
    new SeResSikuliInputText()
  }
}
