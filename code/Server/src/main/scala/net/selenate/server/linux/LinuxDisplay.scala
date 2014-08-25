package net.selenate.server
package linux

import com.ferega.procrun._
import scala.annotation.tailrec

case class DisplayInfo(num: Int, port: Int)

object LinuxDisplay extends Loggable {
  private val screenCache = new SetCache[Int]()
  private val BaseNum = 200

  def create(): DisplayInfo = {
    val num = getFirstFree()
    create(num)
  }

  def destroy(num: Int) {
    logDebug(s"""Destroying screen $num""")
    runPkill(s"x11vnc.*:$num")
    runPkill(s"icewm.*:$num")
    runPkill(s"Xvfb.*:$num")
  }

  def destroyAll() {
    logDebug(s"""Destroying all screens""")
    runPkill(s"x11vnc.*")
    runPkill(s"icewm.*")
    runPkill(s"Xvfb.*")
  }

  val PortR = """PORT=(\d+)"""r
  private def create(num: Int): DisplayInfo = {
    logDebug(s"""Creating screen number $num""")
    screenCache.add(num)

    val result = for {
      xvfb   <- runXvfb(num).right
      iceWM  <- runIceWM(num).right
      x11vnc <- runX11vnc(num).right
    } yield {
      val out = x11vnc.stdOutSoFar.trim
      val PortR(port) = out
      port.toInt
    }

    result match {
      case Left(message)   =>
        val msg = s"""An error occured while creating screen $num:\n$message"""
        logError(msg)
        throw new Exception(msg)
      case Right(port) =>
        logDebug(s"""Screen $num successfully created on port $port""")
        DisplayInfo(num, port)
    }
  }

  private def runXvfb(num: Int)         = LinuxProc.runAndVerify("Xvfb", s":$num" | "-screen" | "0" | "1024x768x16" | "-ac")
  private def runIceWM(num: Int)        = LinuxProc.runAndVerify("icewm", Seq(s"--display=:$num"))
  private def runX11vnc(num: Int)       = LinuxProc.runAndVerify("x11vnc", "-display" | s":$num" | "-listen" | "localhost" | "-nopw" | "-xkb" | "-shared" | "-forever")
  private def runXdpyInfo(num: Int)     = LinuxProc.runAndEnd("xdpyinfo", "-display" | s":$num")
  private def runPkill(pattern: String) = LinuxProc.runAndEnd("pkill", "-f" | pattern)

  @tailrec
  private def getFirstFree(num: Int = BaseNum): Int =
    if (isFree(num)) num else getFirstFree(num+1)

  private def isFree(num: Int) = {
    (num > BaseNum) &&                                      // Sanity check
    runXdpyInfo(num).contains("unable to open display") &&  // System
    !screenCache.contains(num)                              // Thread-safe cache
  }
}
