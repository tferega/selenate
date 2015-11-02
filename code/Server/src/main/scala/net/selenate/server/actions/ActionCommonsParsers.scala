package net.selenate.server
package actions

import java.util.Optional
import net.selenate.common.comms.{ SeCookie => SelenateCookie, _ }
import net.selenate.common.NamedUUID
import org.openqa.selenium.{ Cookie => SeleniumCookie }
import org.openqa.selenium.remote.RemoteWebElement
import org.openqa.selenium.support.ui.Select
import scala.collection.JavaConversions._

trait ActionCommonsParsers extends ActionCommonsBase { self: Loggable =>
  protected def parseAddress(address: Address) =
    new SeAddress(
        address.windowHandle,
        seqToRealJava(address.framePath map toInteger))

  private val elementUuidFactory = new NamedUUID("Element")
  protected def parseWebElement(address: Address, selector: SeElementSelector)(e: RemoteWebElement): Option[SeElement] = {
    val uuid = elementUuidFactory.random()
    context.elementCache.add(uuid, CachedElement(address.windowHandle, address.framePath, e))
    tryo {
      new SeElement(
        uuid,
        selector/*.withUuid(Optional.of(uuid))*/,
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
    }
  }

  protected def parseSelectElement(address: Address, selector: SeElementSelector)(e: RemoteWebElement): Option[SeSelect] = {
    parseWebElement(address, selector)(e).map(new SeSelect(_))
  }

  protected def parseOptionElement(address: Address, selector: SeElementSelector)(e: RemoteWebElement): Option[SeOption] =
    parseWebElement(address, selector)(e).map(new SeOption(_))

  protected def parseCookie(cookie: SeleniumCookie): SelenateCookie =
    new SelenateCookie(
      cookie.getName,
      cookie.getValue,
      cookie.getDomain,
      cookie.getPath,
      cookie.getExpiry,
      cookie.isSecure)
}
