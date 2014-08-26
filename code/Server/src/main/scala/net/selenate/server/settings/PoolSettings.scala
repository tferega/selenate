package net.selenate.server
package settings

object PoolSettings {
  private def parseSize(size: String): Int =
    size match {
      case IsInt(i) => i
      case _ => throw new IllegalArgumentException(s"""Error while parsing configuration. Offending entry: server.pool.size. Expected a number, received "$size".""")
    }

  def fromConfig(
      size: String,
      prefs: Map[String, String],
      display: String,
      binaryLocation: Option[String]) = new PoolSettings(
    size    = parseSize(size),
    profile = ProfileSettings.fromConfig(prefs, display, binaryLocation))
}

case class PoolSettings(size: Int, profile: ProfileSettings)
