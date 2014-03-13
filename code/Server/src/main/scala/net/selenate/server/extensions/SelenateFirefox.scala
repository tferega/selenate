package net.selenate.server
package extensions

import info.ProfileInfo

import org.openqa.selenium.firefox.FirefoxDriver

object SelenateFirefox {
  def fromProfileInfo(parentNum: Option[Int], profile: ProfileInfo) =
    new SelenateFirefox(
        parentNum,
        SelenateBinary.fromProfileInfo(profile),
        SelenateProfile.fromProfileInfo(profile))
}

class SelenateFirefox(val parentNum: Option[Int], val selenateBinary: SelenateBinary, val selenateProfile: SelenateProfile) extends FirefoxDriver(selenateBinary, selenateProfile) {
  def title = this.getTitle
  def title_= (value: String) = this.executeScript(s"window.document.title='$value';")
}
