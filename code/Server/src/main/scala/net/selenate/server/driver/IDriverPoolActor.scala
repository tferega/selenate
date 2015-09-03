package net.selenate
package server
package driver

import org.openqa.selenium.remote.RemoteWebDriver

private[driver] trait IDriverPoolActor {
  def get: RemoteWebDriver
}
