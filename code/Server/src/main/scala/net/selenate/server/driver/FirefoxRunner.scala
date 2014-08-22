package net.selenate.server
package driver

import extensions.{ SelenateBinary, SelenateFirefox, SelenateProfile }
import info.{ DisplayInfo, ProfileInfo }
import linux.{ LinuxDisplay, LinuxFile }

import java.io.File

object FirefoxRunner extends Loggable {
  def run(profile: ProfileInfo) = {
    try {
      profile.display match {
        case DisplayInfo.Main          => runInMain(profile)
        case DisplayInfo.FirstFree     => runInFirstFree(profile)
        case DisplayInfo.Specific(num) => runInSpecific(num, profile)
      }
    } catch {
      case e: Exception =>
        val msg = "An error occured while starting up Firefox!"
        logError(msg, e)
        throw new IllegalArgumentException(msg, e)
    }
  }

  private def runInMain(profile: ProfileInfo) =
    SelenateFirefox.fromProfileInfo(None, profile)

  private def runInFirstFree(profile: ProfileInfo) = {
    if (!C.osName.contains("Linux")) {
      val msg = s"""Display support is available only in Linux (detected OS name: "${ C.osName }")!"""
      logError(msg)
      throw new UnsupportedOperationException(msg)
    }

    val binaryLocation = profile.binaryLocation getOrElse SelenateBinary.DefaultBinaryLocation
    val displayInfo = LinuxDisplay.create()
    val script = createScript(displayInfo.num, binaryLocation)
    val binaryFile = LinuxFile.createTempScript(script)
    val ffBinary = new SelenateBinary(binaryFile)
    val ffProfile = SelenateProfile.fromProfileInfo(profile)
    new SelenateFirefox(Some(displayInfo.num), ffBinary, ffProfile)
  }

  private def runInSpecific(num: Int, profile: ProfileInfo) = {
    throw new UnsupportedOperationException("Specific displays not yet supported!")
  }

  def createScript(displayNum: Int, binaryLocation: File) =
    s"""|#!/bin/bash
        |
        |DISPLAY=:$displayNum $binaryLocation "$$@"""".stripMargin
}
