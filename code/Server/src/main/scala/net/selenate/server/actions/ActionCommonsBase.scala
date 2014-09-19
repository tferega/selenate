package net.selenate.server
package actions

import net.selenate.common.comms.SeElementSelector
import net.selenate.server.extensions.SelenateFirefox

trait ActionCommonsBase {
  type WindowHandle = String
  type FrameNum  = Int
  type FramePath = Vector[FrameNum]
  case class Address(windowHandle: WindowHandle, framePath: FramePath)

  def getFromCache(selector: SeElementSelector): Option[CachedElement] = {
    selector.getUUID.toOption flatMap context.elementCache.get
  }

  val context: SessionContext
  val d: SelenateFirefox
}
