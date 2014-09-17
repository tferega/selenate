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
  def doAct = { arg =>
    val res = waitForPageList(arg.getPageList.toIndexedSeq)
    new SeResBrowserWaitFor(res.isDefined, res.orNull)
  }

  private def waitForPageList(pageList: IndexedSeq[SePage]): Option[SePage] =
    waitForPredicate {
      val resultList = inAllWindows { address =>
        pageList find pageExists
      }
      resultList.find(_.isDefined).flatten
    }

  private def pageExists(page: SePage): Boolean =
    page.getSelectorList
        .map(elementExists)
        .forall(identity)

  private def elementExists(selector: SeElementSelector): Boolean =
    findElementList(selector).size > 0
}
