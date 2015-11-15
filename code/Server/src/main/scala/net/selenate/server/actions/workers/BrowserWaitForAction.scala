package net.selenate.server
package actions
package workers

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqBrowserWaitFor
import net.selenate.common.comms.res.SeResBrowserWaitFor
import net.selenate.common.comms.{ SeElementSelector, SePage }
import scala.collection.JavaConversions._

object BrowserWaitForAction {
  val PersistentPageName = "6bebd676-a450-40a6-b2c9-f0d02dd2cf0a"
}

class BrowserWaitForAction(val sessionID: String, val context: SessionContext, val d: SelenateFirefox)
    extends Action[SeReqBrowserWaitFor, SeResBrowserWaitFor]
    with ActionCommons
    with WaitFor {
  import BrowserWaitForAction._
  case class OwnedSelector(pageName: String, selector: SeElementSelector, isPresent: Boolean)

  protected val timeout = context.waitTimeout
  protected val resolution = context.waitResolution
  protected val delay = context.waitDelay

  def doAct = { arg =>
    val persistentPage = new SePage(PersistentPageName, context.persistentPresentSelectorList, context.persistentAbsentSelectorList);
    val pages = arg.getPageList.toIndexedSeq :+ persistentPage
    val res = waitForPageList(pages)
    new SeResBrowserWaitFor(res.isDefined, res.orNull)
  }

  private def waitForPageList(pageList: IndexedSeq[SePage]): Option[SePage] =
    waitForPredicate {
      val selectorList = getSelectorList(pageList)
      val resultList   = filterSelectorList(selectorList)
      val foundPageList = pageList filter isPageFound(resultList)
      if (foundPageList.exists(_.getName == PersistentPageName)) {
        foundPageList find (_.getName != PersistentPageName)
      } else {
        None
      }
    }

  private def getSelectorList(pageList: IndexedSeq[SePage]): IndexedSeq[OwnedSelector] =
      for {
        page <- pageList
        (selector, isPresent) <-
            page.getPresentSelectorList.map((_, true)) ++
            page.getAbsentSelectorList.map((_, false))
      } yield {
        OwnedSelector(page.getName, selector, isPresent)
      }

  private def filterSelectorList(selectorList: IndexedSeq[OwnedSelector]): IndexedSeq[OwnedSelector] =
      inAllWindows { address =>
        selectorList filter elementExists
      }.force.flatten

  private def elementExists(selector: OwnedSelector): Boolean = {
    val isFound = !findElementList(selector.selector).isEmpty
    if (selector.isPresent) isFound else !isFound
  }

  private def isPageFound(resultList: IndexedSeq[OwnedSelector])(page: SePage): Boolean = {
    val targetSize = page.getPresentSelectorList.size + page.getAbsentSelectorList.size
    val resultSize = resultList.filter(_.pageName == page.getName).size
    targetSize == resultSize
  }
}
