package net.selenate.server
package linux

import com.ferega.procrun._

object WindowInfo {
  def fromString(raw: String) =
    raw.split(" +").toList match {
      case windowID :: desktopID :: IsInt(left) :: IsInt(top) :: IsInt(width) :: IsInt(height) :: client :: rest =>
        WindowInfo(windowID, desktopID, left, top, width, height, client, rest.mkString(" "))
      case _ =>
        throw new RuntimeException(s"""Error while parsing WindowInfo. Received malformed output from wmctrl: "$raw".""")
    }
}
case class WindowInfo(
    windowID: String,
    desktopID: String,
    left: Int,
    top: Int,
    width: Int,
    height: Int,
    client: String,
    title: String)

object LinuxWindow extends Loggable {
  def clickRelative(num: Option[Int], titlePart: String, relX: Int, relY: Int) {
    logDebug(s"Clicking on relative coordinates ($relX, $relY) within window $titlePart in screen $num")
    val windowInfo = getWindowInfo(num, titlePart)
    logTrace(s"Window info: $windowInfo")
    moveMouseRelative(num, windowInfo, relX, relY)
  }

  def input(num: Option[Int], input: String) {
    val preparedInput = input.toSeq
    runXdotoolInput(num, preparedInput)
  }

  private def getWindowInfo(num: Option[Int], titlePart: String) = {
    val rawList = runWmctrl(num).split("\n")
    val rawOpt = rawList.filter(_.contains(titlePart)).headOption
    val raw = rawOpt.getOrElse {
      val msg = s"""Error while searching for a window. Window with title containing $titlePart not found."""
      logError(msg)
      throw new IllegalArgumentException(msg)
    }
    WindowInfo.fromString(raw)
  }

  private def moveMouseRelative(num: Option[Int], windowInfo: WindowInfo, relX: Int, relY: Int) {
    val absX = windowInfo.left + relX
    val absY = windowInfo.top  + relY
    runXdotoolActivate(num, windowInfo.windowID)
    runXdotoolMove(num, absX, absY)
    runXdotoolClick(num)
  }

  private def runWmctrl(num: Option[Int]) =
    LinuxProc.runAndEnd("wmctrl", "-l" | "-G", num = num)

  private def runXdotoolActivate(num: Option[Int], windowID: String) =
    LinuxProc.runAndEnd("xdotool", "windowactivate" | windowID, num = num)

  private def runXdotoolMove(num: Option[Int], x: Int, y: Int) =
    LinuxProc.runAndEnd("xdotool", "mousemove" | x | y, num = num)

  private def runXdotoolClick(num: Option[Int]) =
    LinuxProc.runAndEnd("xdotool", "click" | 1, num = num)

  private def runXdotoolInput(num: Option[Int], input: Seq[Char]) =
    LinuxProc.runAndEnd("xdotool", Seq("key") ++ input, num = num)
}
