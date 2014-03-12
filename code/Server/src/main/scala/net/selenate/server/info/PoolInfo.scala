package net.selenate.server
package info

object PoolInfo {
  private val DefaultName    = "default"
  private val DefaultSize    = 5
  private val DefaultProfile = ProfileInfo.empty

  private def parseSize(name: String)(size: String): Int =
    size match {
      case IsInt(i) => i
      case _ => throw new IllegalArgumentException(s"""Error while parsing configuration. Offending entry: server.pool.$name.size. Expected a number, received "$size".""")
    }

  def empty(name: String) = new PoolInfo(
      name    = name,
      size    = DefaultSize,
      profile = DefaultProfile)

  def default = empty(DefaultName)

  def fromConfig(
      name: String,
      size: Option[String],
      prefs: Option[String],
      display: Option[String],
      binaryLocation: Option[String]) = new PoolInfo(
    name           = name,
    size           = size.map(parseSize(name)).getOrElse(DefaultSize),
    profile        = ProfileInfo.fromConfig(name, prefs, display, binaryLocation))
}


class PoolInfo(
    val name: String,
    val size: Int            = PoolInfo.DefaultSize,
    val profile: ProfileInfo = PoolInfo.DefaultProfile) {
}
