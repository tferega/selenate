package net.selenate.server
package driver

import org.openqa.selenium.firefox.FirefoxDriver

private[driver] trait IDriverPoolActor {
  def signature: String
  def get: FirefoxDriver
}
