package net.selenate.server
package driver

import extensions.SelenateFirefox
import info.{ PoolInfo, ProfileInfo }

private[driver] trait IDriverPoolActor {
  def info: PoolInfo
  def profile: ProfileInfo
  def get: SelenateFirefox
}
