package net.selenate.server
package driver

import extensions.{ SelenateBinary, SelenateFirefox, SelenateProfile }
import info.{ DisplayInfo, ProfileInfo }
import linux.{ LinuxDisplay, LinuxFile }

import java.io.File

object FirefoxRunner {
  def run(profile: ProfileInfo) =
    profile.display match {
      case DisplayInfo.Main          => runInMain(profile)
      case DisplayInfo.FirstFree     => runInFirstFree(profile)
      case DisplayInfo.Specific(num) => runInSpecific(num, profile)
    }

  private def runInMain(profile: ProfileInfo) =
    SelenateFirefox.fromProfileInfo(profile)

  private def runInFirstFree(profile: ProfileInfo) = {
    val binaryLocation = profile.binaryLocation getOrElse SelenateBinary.DefaultBinaryLocation
    val displayInfo = LinuxDisplay.create()
    println(displayInfo)
    val script = createScript(displayInfo.num, binaryLocation)
    val binaryFile = LinuxFile.createTempScript(script)
    val ffBinary = new SelenateBinary(binaryFile)
    val ffProfile = SelenateProfile.fromProfileInfo(profile)
    new SelenateFirefox(ffBinary, ffProfile)
  }

  private def runInSpecific(num: Int, profile: ProfileInfo) = {
    throw new UnsupportedOperationException("Specific displays not yet supported.")
  }

  def createScript(displayNum: Int, binaryLocation: File) =
    s"""|#!/bin/bash
        |
        |DISPLAY=:$displayNum $binaryLocation "$$@"""".stripMargin
}
