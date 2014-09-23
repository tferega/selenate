package net.selenate.server
package actions
package workers

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqBrowserWaitFor
import net.selenate.common.comms.res.SeResBrowserWaitFor
import net.selenate.common.comms.{ SeElementSelector, SePage }
import scala.collection.JavaConversions._

class BrowserWaitForAction(val sessionID: String, val context: SessionContext, val d: SelenateFirefox)
    extends Action[SeReqBrowserWaitFor, SeResBrowserWaitFor]
    with ActionCommons
    with WaitFor {
  case class OwnedSelector(pageName: String, selector: SeElementSelector)

  def doAct = { arg =>
    val res = waitForPageList(arg.getPageList.toIndexedSeq)
    new SeResBrowserWaitFor(res.isDefined, res.orNull)
  }

  private def waitForPageList(pageList: IndexedSeq[SePage]): Option[SePage] =
    waitForPredicate {
      val selectorList = getSelectorList(pageList)
      val resultList   = filterSelectorList(selectorList)
      pageList find isPageFound(resultList)
    }

  private def getSelectorList(pageList: IndexedSeq[SePage]): IndexedSeq[OwnedSelector] =
      for {
        page     <- pageList
        selector <- page.getSelectorList
      } yield {
        OwnedSelector(page.getName, selector)
      }

  private def filterSelectorList(selectorList: IndexedSeq[OwnedSelector]): IndexedSeq[OwnedSelector] =
      inAllWindows { address =>
        selectorList filter elementExists
      }.force.flatten

  private def elementExists(selector: OwnedSelector): Boolean =
    findElementList(selector.selector).size > 0

  private def isPageFound(resultList: IndexedSeq[OwnedSelector])(page: SePage): Boolean = {
    val targetSize = page.getSelectorList.size
    val resultSize = resultList.filter(_.pageName == page.getName).size
    targetSize == resultSize
  }
}
