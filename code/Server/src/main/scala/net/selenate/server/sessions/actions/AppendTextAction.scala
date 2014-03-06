package net.selenate.server
package sessions.actions

import net.selenate.common.comms.req.SeReqAppendText
import net.selenate.common.comms.res.SeResAppendText
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.internal.selenesedriver.SendKeys
import scala.collection.JavaConversions._

class AppendTextAction(val d: FirefoxDriver)(implicit context: ActionContext)
    extends IAction[SeReqAppendText, SeResAppendText]
    with ActionCommons {
  protected val log = Log(classOf[AppendTextAction])

  def act = { arg =>
    switchToFrame(arg.windowHandle, arg.framePath.map(_.toInt).toIndexedSeq)
    val e = findElement(arg.method, arg.query)
    e.sendKeys(arg.text)

    new SeResAppendText()
  }
}
