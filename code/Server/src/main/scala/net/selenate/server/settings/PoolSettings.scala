package net.selenate.server
package settings

import net.selenate.common.exceptions.SeException

object PoolSettings {
  private def parseSize(size: String): Int =
    size match {
      case IsInt(i) if i > 0 => i
      case IsInt(i) => throw new SeException(s"""Error while parsing configuration. Offending entry: server.pool.size. Size must be greater than zero, received "$size".""")
      case _ => throw new SeException(s"""Error while parsing configuration. Offending entry: server.pool.size. Expected a number, received "$size".""")
    }

  def fromConfig(
      size: String,
      display: String,
      binaryLocation: Option[String],
      prefs: Map[String, String]) = new PoolSettings(
    size    = parseSize(size),
    profile = ProfileSettings.fromConfig(display, binaryLocation, prefs))
}

case class PoolSettings(size: Int, profile: ProfileSettings)
