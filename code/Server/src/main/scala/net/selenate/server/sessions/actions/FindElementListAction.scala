package net.selenate
package server
package sessions
package actions

import common.comms.res._
import common.comms.req._

import org.openqa.selenium.remote.RemoteWebDriver


class FindElementListAction(val d: RemoteWebDriver)(implicit context: ActionContext)
    extends RetryableAction[SeReqFindElementList, SeResFindElementList]
    with ActionCommons {

  protected val log = Log(classOf[FindElementListAction])

  def retryableAct = { arg =>
    val resElementList = inAllWindows { address =>
      val webElementList = findElementList(arg.method, arg.query)
      webElementList map parseWebElement(address)
    }

    new SeResFindElementList(seqToRealJava(resElementList.flatten))
  }
}
