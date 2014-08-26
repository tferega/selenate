package net.selenate.server
package extensions

import settings.ProfileSettings

import org.openqa.selenium.firefox.FirefoxDriver

object SelenateFirefox {
  def fromProfileSettings(parentNum: Option[Int], profile: ProfileSettings) =
    new SelenateFirefox(
        parentNum,
        SelenateBinary.fromProfileSettings(profile),
        SelenateProfile.fromProfileSettings(profile))
}

class SelenateFirefox(val parentNum: Option[Int], val selenateBinary: SelenateBinary, val selenateProfile: SelenateProfile) extends FirefoxDriver(selenateBinary, selenateProfile) {
  def title = this.getTitle
  def title_= (value: String) = this.executeScript(s"window.document.title='$value';")
}
