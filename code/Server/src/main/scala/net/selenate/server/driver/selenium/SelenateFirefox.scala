package net.selenate.server
package driver
package selenium

import org.openqa.selenium.firefox.FirefoxDriver

object SelenateFirefox {
  def fromProfileInfo(profile: ProfileInfo) =
    new SelenateFirefox(
        SelenateBinary.fromProfileInfo(profile),
        SelenateProfile.fromProfileInfo(profile))
}

class SelenateFirefox(val selenateBinary: SelenateBinary, val selenateProfile: SelenateProfile) extends FirefoxDriver(selenateBinary, selenateProfile)
