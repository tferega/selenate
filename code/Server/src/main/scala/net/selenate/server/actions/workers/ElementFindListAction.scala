package net.selenate.server
package actions
package workers

import extensions.SelenateFirefox

import net.selenate.common.comms.SeElement
import net.selenate.common.comms.req.SeReqElementFindList
import net.selenate.common.comms.res.SeResElementFindList
import org.openqa.selenium.remote.RemoteWebElement
import scala.collection.JavaConversions._

class ElementFindListAction(val sessionID: String, val context: SessionContext, val d: SelenateFirefox)
    extends Action[SeReqElementFindList, SeResElementFindList]
    with ActionCommons {
  def doAct = { arg =>
    val finder = if (arg.getParentSelector != null) {
      findWithParent _
    } else {
      findWithoutParent _
    }

    val res = finder(arg)
    new SeResElementFindList(seqToRealJava(res))
  }

  private def findWithParent(arg: SeReqElementFindList): IndexedSeq[SeElement] = {
    val parentBy = byFactory(arg.getParentSelector);
    val targetBy = byFactory(arg.getTargetSelector);
    inAllWindows { address =>
      val parentWebElement = d.findElement(parentBy).asInstanceOf[RemoteWebElement]
      parentWebElement.findElements(targetBy)
          .map(_.asInstanceOf[RemoteWebElement])
          .flatMap(parseWebElement(address, arg.getParentSelector))
          .toIndexedSeq
    }.take(1).flatten.force
  }

  private def findWithoutParent(arg: SeReqElementFindList): IndexedSeq[SeElement] = {
    inAllWindows { address =>
      val webElementList = findElementList(arg.getTargetSelector)
      webElementList flatMap parseWebElement(address, arg.getTargetSelector)
    }.flatten.force
  }
}
