package net.selenate.server
package driver

import linux.{ Display, Files }
import selenium.{ SelenateBinary, SelenateFirefox, SelenateProfile }

import java.io.File
import org.apache.commons.io.FileUtils

object FirefoxRunner {
  def run(profile: ProfileInfo) =
    profile.screenPreference match {
      case ScreenPreference.Default   => runInDefaultScreen(profile)
      case ScreenPreference.FirstFree => runInFirstFreeScreen(profile)
    }

  private def runInDefaultScreen(profile: ProfileInfo) =
    SelenateFirefox.fromProfileInfo(profile)

  private def runInFirstFreeScreen(profile: ProfileInfo) = {
    val binaryLocation = profile.binaryLocation getOrElse SelenateBinary.DefaultBinaryLocation
    val displayInfo = Display.create()
    val script = createScript(displayInfo.num, binaryLocation)
    val binaryFile = Files.createTempScript(script)
    val ffBinary = new SelenateBinary(binaryFile)
    val ffProfile = SelenateProfile.fromProfileInfo(profile)
    new SelenateFirefox(ffBinary, ffProfile)
  }

  def createScript(displayNum: Int, binaryLocation: File) =
    s"""|#!/bin/bash
        |
        |DISPLAY=:$displayNum $binaryLocation "$$@"""".stripMargin
}
