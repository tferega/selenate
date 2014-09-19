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

  private def doSwitch[T](f: Address => T, window: WindowHandle, framePath: FramePath, frameNum: FrameNum): Iterator[T] = {
    frameSwitch(frameNum)
    println("    DOING " + (framePath :+ frameNum).mkString("[", ", ", "]"))
    val r = inAllFramesDoit(f, window, framePath :+ frameNum)
    frameBack()
    r
  }

  private def inAllFramesDoit[T](f: Address => T, window: WindowHandle, framePath: FramePath): Iterator[T] = {
    val frameCount = findAllFrames().size
    val result = f(Address(window, framePath))
    val childrenResult =
    for {
      frameNum <- (0 until frameCount).iterator
      result   <- doSwitch(f, window, framePath, frameNum)
    } yield (result)
    childrenResult ++ Iterator.single(result)
  }

  protected def inAllFrames[T](f: Address => T)(window: WindowHandle): Iterator[T] = {
    frameReset()
    inAllFramesDoit(f, window, Vector.empty)
  }

  protected def inAllWindows[T](f: Address => T): Iterator[T] = {
    val windowList: Iterator[WindowHandle] = d.getWindowHandles.toIterator
    windowList flatMap inAllFrames(f)
  }
}
