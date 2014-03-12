package net.selenate.server
package driver

import info.{ PoolInfo, ProfileInfo }
import selenium.SelenateFirefox

private[driver] trait IDriverPoolActor {
  def info: PoolInfo
  def profile: ProfileInfo
  def get: SelenateFirefox
}
