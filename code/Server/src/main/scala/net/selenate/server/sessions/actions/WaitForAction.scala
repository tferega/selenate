package net.selenate
package server
package sessions
package actions

import common.comms.res._
import common.comms.req._

import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.{ By, WebElement }

import scala.collection.JavaConversions._
import scala.annotation.tailrec

object WaitForAction {
  private val timeout    = 20000
  private val resolution = 250

  def waitForPredicate(predicate: => Boolean): Boolean = {
    @tailrec
    def waitForDoit(end: Long, resolution: Long, predicate: => Boolean): Boolean = {
      val current = System.currentTimeMillis
      val remaining = end - current

      if (remaining < 0) {
        false  // Timeout
      } else {
        if (predicate) {
          true  // Predicate evaluated to true
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

  def act = { arg =>
    val res = waitForElementList(arg.selectorList.toIndexedSeq)

    new SeResWaitFor(res)
  }


  def waitForElementList(selectorList: IndexedSeq[SeReqElementSelector]): Boolean = {
    waitForPredicate {
      (selectorList map elementExists).foldLeft(true)(_ && _)
    }
  }

  def elementExists(selector: SeReqElementSelector): Boolean = {
    try {
      val elemOpt = inAllFrames {
        tryo {
          findElement(selector.method, selector.query)
        }
      }

      elemOpt.exists(_.isDefined)
    } catch {
      case e: Exception => false
    }
  }
}