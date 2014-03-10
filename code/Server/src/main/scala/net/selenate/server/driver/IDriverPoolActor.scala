package net.selenate.server
package driver

import selenium.SelenateFirefox

private[driver] trait IDriverPoolActor {
  def signature: String
  def get: SelenateFirefox
}
