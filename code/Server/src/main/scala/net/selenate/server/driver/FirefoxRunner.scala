package net.selenate.server
package driver

import extensions.{ SelenateBinary, SelenateFirefox, SelenateProfile }
import settings.{ DisplaySettings, ProfileSettings }
import linux.{ LinuxDisplay, LinuxFile }

import java.io.File
import java.util.concurrent.TimeUnit

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
        throw new IllegalArgumentException(msg, e)
    }
  }

  private def runInMain(profile: ProfileSettings) =
    SelenateFirefox.fromProfileSettings(None, profile)

  private def runInFirstFree(profile: ProfileSettings) = {
    if (!C.OS_NAME.contains("Linux")) {
      val msg = s"""Display support is available only in Linux (detected OS name: "${ C.OS_NAME }")!"""
      logError(msg)
      throw new UnsupportedOperationException(msg)
    }

    val binaryLocation = profile.binaryLocation getOrElse SelenateBinary.DefaultBinaryLocation
    val displayInfo = LinuxDisplay.create()
    val script = createScript(displayInfo.num, binaryLocation)
    val binaryFile = LinuxFile.createTempScript(script)
    val ffBinary = new SelenateBinary(binaryFile)
    val ffProfile = SelenateProfile.fromProfileSettings(profile)
    val d = new SelenateFirefox(profile, Some(displayInfo), ffBinary, ffProfile)
    d.manage.timeouts.pageLoadTimeout(C.Server.Timeouts.PAGE_LOAD , TimeUnit.SECONDS)
    d
  }

  private def runInSpecific(num: Int, profile: ProfileSettings) = {
    throw new UnsupportedOperationException("Specific displays not yet supported!")
  }

  def createScript(displayNum: Int, binaryLocation: File) =
    s"""|#!/bin/bash
        |
        |DISPLAY=:$displayNum $binaryLocation "$$@"""".stripMargin
}
