package net.selenate.server
package driver

import org.openqa.selenium.firefox.FirefoxDriver

object FirefoxRunner {
  def run(dp: DriverProfile) = {
    new FirefoxDriver(dp.ffBinary, dp.ffProfile)
  }
}
