package net.selenate.server
package driver

import extensions.{ SelenateBinary, SelenateFirefox, SelenateProfile }
import linux.{ LinuxDisplay, LinuxFile }
import settings.{ DisplaySettings, ProfileSettings }

import java.io.File
import java.util.concurrent.TimeUnit
import net.selenate.common.exceptions.SeException

object FirefoxRunner extends Loggable {
  def run(profile: ProfileSettings) = {
    try {
      profile.display match {
        case DisplaySettings.Main          => runInMain(profile)
        case DisplaySettings.FirstFree     => runInFirstFree(profile)
        case DisplaySettings.Specific(num) => runInSpecific(num, profile)
      }
    } catch {
      case e: Exception =>
        val msg = "An error occured while starting up Firefox!"
        logError(msg, e)
        throw new SeException(msg, e)
    }
  }

  private def runInMain(profile: ProfileSettings) = {
    val d = SelenateFirefox.fromProfileSettings(None, profile)
    val duration = C.Server.Timeouts.PAGE_LOAD.duration;
    d.manage.timeouts.pageLoadTimeout(duration.length, duration.unit)
    d
  }

  private def runInFirstFree(profile: ProfileSettings) = {
    if (!C.OS_NAME.contains("Linux")) {
      val msg = s"""Display support is available only in Linux (detected OS name: "${ C.OS_NAME }")!"""
      logError(msg)
      throw new SeException(msg)
    }

    val binaryLocation = profile.binaryLocation getOrElse SelenateBinary.DefaultBinaryLocation
    val displayInfo = LinuxDisplay.create()
    val script = createScript(displayInfo.num, binaryLocation)
    val binaryFile = LinuxFile.createTempScript(script)
    val ffBinary = new SelenateBinary(binaryFile)
    val ffProfile = SelenateProfile.fromProfileSettings(profile)
    val d = new SelenateFirefox(profile, Some(displayInfo), ffBinary, ffProfile)
    val duration = C.Server.Timeouts.PAGE_LOAD.duration;
    d.manage.timeouts.pageLoadTimeout(duration.length, duration.unit)
    d
  }

  private def runInSpecific(num: Int, profile: ProfileSettings) = {
    throw new SeException("Specific displays not yet supported!")
  }

  def createScript(displayNum: Int, binaryLocation: File) = {
    val width = C.Server.Pool.DISPLAY_WIDTH
    val height = C.Server.Pool.DISPLAY_HEIGHT
    s"""|#!/bin/bash
        |
        |DISPLAY=:$displayNum $binaryLocation -width $width -height $height "$$@"""".stripMargin
  }
}
