package net.selenate.server
package sessions.actions

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqWaitFor
import net.selenate.common.comms.res.SeResWaitFor
import net.selenate.common.comms.{ SeElementSelector, SePage }
import scala.collection.JavaConversions._

class WaitForAction(val d: SelenateFirefox)(implicit context: ActionContext)
    extends Action[SeReqWaitFor, SeResWaitFor]
    with ActionCommons
    with WaitFor {
  def act = { arg =>
    val res = waitForPageList(arg.pageList.toIndexedSeq)

    new SeResWaitFor(res.isDefined, res.orNull)
  }


  def waitForPageList(pageList: IndexedSeq[SePage]): Option[String] =
    waitForPredicate {
      pageList find pageExists map (_.name)
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
