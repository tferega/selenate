package net.selenate.server.driver

import org.openqa.selenium.firefox.FirefoxDriver

trait IDriverPool {
  def get: FirefoxDriver
}