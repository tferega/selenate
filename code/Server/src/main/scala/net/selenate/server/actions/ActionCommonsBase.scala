package net.selenate.server
package actions

import net.selenate.server.extensions.SelenateFirefox

trait ActionCommonsBase {
  type WindowHandle = String
  type FrameNum = Int
  type FramePath = IndexedSeq[FrameNum]
  case class Address(windowHandle: WindowHandle, framePath: FramePath)

  val d: SelenateFirefox
}
