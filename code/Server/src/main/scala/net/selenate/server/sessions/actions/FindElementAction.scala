package net.selenate
package server
package sessions
package actions

import common.comms.res._
import common.comms.req._
import org.openqa.selenium.remote.RemoteWebDriver


class FindElementAction(val d: RemoteWebDriver)(implicit context: ActionContext)
    extends RetryableAction[SeReqFindElement, SeResFindElement]
    with ActionCommons {

  protected val log = Log(classOf[FindElementAction])

  def retryableAct = { arg =>
    val resElementList: Stream[Option[SeResFindElement]] = inAllWindows { address =>
      tryo {
        val webElement = findElement(arg.method, arg.query)
        val resElement = parseWebElement(address)(webElement)
        new SeResFindElement(resElement)
      }
    }

    val e = resElementList.flatten
    if (e.isEmpty) {
      throw new IllegalArgumentException("Element [%s, %s] was not found in any frame!".format(arg.method.toString, arg.query))
    } else {
      e.head
    }
  }
}
