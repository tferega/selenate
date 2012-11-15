package net.selenate.server
package sessions
package actions

import comms.res._
import comms.req._

import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.By

class ClickAction(val d: FirefoxDriver) extends IAction[SeReqClick, SeResClick] {
  def act = { arg =>
    val byFactory = arg.method match {
      case SeReqSelectMethod.CLASS_NAME        => By.className _
      case SeReqSelectMethod.CSS_SELECTOR      => By.cssSelector _
      case SeReqSelectMethod.ID                => By.id _
      case SeReqSelectMethod.LINK_TEXT         => By.linkText _
      case SeReqSelectMethod.NAME              => By.name _
      case SeReqSelectMethod.PARTIAL_LINK_TEXT => By.partialLinkText _
      case SeReqSelectMethod.TAG_NAME          => By.tagName _
      case SeReqSelectMethod.XPATH             => By.xpath _
    }

    val by = byFactory(arg.selector)
    d.findElement(by).click

    new SeResClick()
  }
}