package net.selenate.server
package actions

import net.selenate.server.Loggable
import scala.collection.JavaConversions._

trait ActionCommonsFrames extends ActionCommonsBase { self: Loggable =>
  protected def windowSwitch(handle: WindowHandle) {
    logTrace(s"Switching to window $handle")
    d.switchTo.window(handle)
  }

  protected def frameReset() {
    logTrace("Resetting frame")
    d.switchTo.defaultContent
  }

  protected def frameSwitch(num: FrameNum) {
    logTrace(s"Switching to frame $num }")
    d.switchTo.frame(num)
  }

  protected def frameBack() {
    logTrace("Switcing to parent frame")
    d.switchTo.parentFrame
  }

  protected def fullSwitch(windowHandle: WindowHandle, framePath: FramePath) {
    logTrace("Resetting window and frame")
    d.switchTo.defaultContent
    d.switchTo.window(windowHandle)
    framePath foreach d.switchTo().frame
  }

  protected def findAllFrames() =
    d.findElementsByXPath("//*[local-name()='frame' or local-name()='iframe']")

  protected def emptyAddress() = Address(d.getWindowHandle, Vector.empty)

  protected def inAllFrames[T](f: Address => T)(window: WindowHandle): Iterator[T] = {
    if (context.useFrames) {
      logTrace("inAllFranes searching through all frames")
      frameReset()
      inAllFramesDoit(f, window, Vector.empty)
    } else {
      logTrace("inAllFrames searching only in current frame")
      val address = Address(window, Vector.empty)
      Iterator.single(f(address))
    }
  }

  protected def inAllWindows[T](f: Address => T): Iterator[T] = {
    if (context.useFrames) {
      logTrace("inAllWindows searching through all windows")
      val windowList: Iterator[WindowHandle] = d.getWindowHandles.toIterator
      windowList flatMap { handler =>
        windowSwitch(handler)
        inAllFrames(f)(handler)
      }
    } else {
      logTrace("inAllWindows searching only in current window")
      val result = f(emptyAddress())
      Iterator.single(result)
    }
  }

  private def doSwitch[T](f: Address => T, window: WindowHandle, framePath: FramePath, frameNum: FrameNum): Iterator[T] = {
    frameSwitch(frameNum)
    val r = inAllFramesDoit(f, window, framePath :+ frameNum)
    frameBack()
    r
  }

  private def inAllFramesDoit[T](f: Address => T, window: WindowHandle, framePath: FramePath): Iterator[T] = {
    val frameCount = findAllFrames().size
    val address = Address(window, framePath)
    logTrace(s"Executing function in $address")
    val result = f(address)
    val childrenResult =
    for {
      frameNum <- (0 until frameCount).iterator
      result   <- doSwitch(f, window, framePath, frameNum)
    } yield (result)
    childrenResult ++ Iterator.single(result)
  }
}
