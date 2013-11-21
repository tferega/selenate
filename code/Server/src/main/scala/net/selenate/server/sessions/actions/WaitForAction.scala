package net.selenate
package server
package sessions
package actions

import common.comms._
import res._
import req._

import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.{ By, WebElement }

import scala.collection.JavaConversions._
import scala.annotation.tailrec

object WaitForAction {
  private val timeout    = 20000
  private val resolution = 250

  def waitForPredicate[T](predicate: => Option[T]): Option[T] = {
    @tailrec
    def waitForDoit(end: Long, resolution: Long, predicate: => Option[T]): Option[T] = {
      val current = System.currentTimeMillis
      val remaining = end - current

      if (remaining < 0) {
        None  // Timeout
      } else {
        val p = predicate
        if (p.isDefined) {
          p  // Predicate evaluated to true
        } else {
          // Do not oversleep.
          val sleep = scala.math.min(resolution, remaining)
          Thread.sleep(sleep)
          waitForDoit(end, resolution, predicate)
        }
      }
    }

    val end = System.currentTimeMillis + timeout
    waitForDoit(end, resolution, predicate)
  }
}


class WaitForAction(val d: FirefoxDriver)
    extends IAction[SeReqWaitFor, SeResWaitFor]
    with ActionCommons {
  import WaitForAction._

  protected val log = Log(classOf[WaitForAction])

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
