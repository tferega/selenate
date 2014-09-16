package net.selenate.server
package actions

import net.selenate.common.comms.{ SeAddress, SeCookie, SeElement, SeOption, SeSelect }
import org.openqa.selenium.Cookie
import org.openqa.selenium.remote.RemoteWebElement
import org.openqa.selenium.support.ui.Select
import scala.collection.JavaConversions._

trait ActionCommonsParsers extends ActionCommonsBase { self: Loggable =>
  protected def parseAddress(address: Address) =
    new SeAddress(
        address.windowHandle,
        seqToRealJava(address.framePath map toInteger))

  protected def parseWebElement(address: Address)(e: RemoteWebElement) =
    new SeElement(
        e.getId,
        e.getLocation.getX,
        e.getLocation.getY,
        e.getSize.getWidth,
        e.getSize.getHeight,
        e.getTagName,
        e.getText,
        e.isDisplayed,
        e.isEnabled,
        e.isSelected,
        parseAddress(address))

  protected def parseSelectElement(address: Address)(e: RemoteWebElement): SeSelect = {
    val select = new Select(e)
    val rawAllOptionList         = select.getOptions.map(_.asInstanceOf[RemoteWebElement]).toIndexedSeq
    val rawSelectedOptionList    = select.getAllSelectedOptions.map(_.asInstanceOf[RemoteWebElement]).toIndexedSeq
    val selectedIndexList        = rawAllOptionList.zipWithIndex.collect { case(o, i) if rawSelectedOptionList.contains(o) => i }

    val parsedAllOptionList      = rawAllOptionList map parseOptionElement(address)
    val parsedSelectedOptionList = rawSelectedOptionList map parseOptionElement(address)

    val firstSelectedIndex       = selectedIndexList.headOption
    val firstSelectedOption      = parsedSelectedOptionList.headOption

    new SeSelect(
        parseWebElement(address)(e),
        parsedAllOptionList.size,
        firstSelectedIndex map toInteger orNull,
        firstSelectedOption.orNull,
        seqToRealJava(parsedAllOptionList))
  }

  protected def parseOptionElement(address: Address)(e: RemoteWebElement): SeOption =
    new SeOption(parseWebElement(address)(e))

  protected def parseCookie(cookie: Cookie): SeCookie =
    new SeCookie(
      cookie.getName,
      cookie.getValue,
      cookie.getDomain,
      cookie.getPath,
      cookie.getExpiry,
      cookie.isSecure)
}
