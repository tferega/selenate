package net.selenate.server
package sessions.actions

import net.selenate.common.comms.req.SeReqWaitForBrowserPage
import net.selenate.common.comms.res.SeResWaitForBrowserPage
import net.selenate.common.comms.{ SeElementSelector, SePage }
import org.openqa.selenium.firefox.FirefoxDriver
import scala.collection.JavaConversions._

class WaitForBrowserPageAction(val d: FirefoxDriver)(implicit context: ActionContext)
    extends IAction[SeReqWaitForBrowserPage, SeResWaitForBrowserPage]
    with ActionCommons
    with WaitFor {
  protected val log = Log(classOf[WaitForBrowserPageAction])

  def act = { arg =>
    val res = waitForPageList(arg.pageList.toIndexedSeq)

    val ret = res match {
      case Some(x) => toBrowserPage(x);
      case _       => null;
    }

    // TODO SeResWaitForBrowserPage BasePage => SePage and conversion somewhere else
    new SeResWaitForBrowserPage(res.isDefined, ret)
  }


  def waitForPageList(pageList: IndexedSeq[SePage]): Option[SePage] =
    waitForPredicate {
      pageList find pageExists
    }

  def pageExists(page: SePage): Boolean =
    (page.selectorList map elementExists).foldLeft(true)(_ && _)

  def elementExists(selector: SeElementSelector): Boolean =
    try {
      val elemOpt = inAllWindows { address =>
        tryo {
          findElement(selector.method, selector.query)
        }
      }
      elemOpt.exists(_.isDefined)
    } catch {
      case e: Exception => false
    }
}
