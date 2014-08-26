package net.selenate.server
package sessions.actions

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqFindElement
import net.selenate.common.comms.res.SeResFindElement

class FindElementAction(val sessionID: String, val d: SelenateFirefox)(implicit context: ActionContext)
    extends Action[SeReqFindElement, SeResFindElement]
    with ActionCommons {
  def act = { arg =>
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
      e(0)
    }
  }
}
