package net.selenate.server
package extensions

import linux.DisplayInfo
import settings.ProfileSettings

import org.openqa.selenium.firefox.FirefoxDriver

object SelenateFirefox {
  def fromProfileSettings(displayInfo: Option[DisplayInfo], profile: ProfileSettings) =
    new SelenateFirefox(
        profile,
        displayInfo,
        SelenateBinary.fromProfileSettings(profile),
        SelenateProfile.fromProfileSettings(profile))
}

class SelenateFirefox(val profile: ProfileSettings, val displayInfo: Option[DisplayInfo], val selenateBinary: SelenateBinary, val selenateProfile: SelenateProfile) extends FirefoxDriver(selenateBinary, selenateProfile) {
  def title = this.getTitle
  def title_= (value: String) = this.executeScript(s"window.document.title='$value';")
}
