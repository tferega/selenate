package net.selenate.server
package info

object PoolInfo {
  private def parseSize(name: String, size: String): Int =
    size match {
      case IsInt(i) => i
      case _ => throw new IllegalArgumentException(s"""Error while parsing configuration. Offending entry: server.pool.$name.size. Expected a number, received "$size".""")
    }

  val default = C.Server.Pool.defaultPool

  def fromConfig(
      name: String,
      size: String,
      prefs: Map[String, String],
      display: String,
      binaryLocation: Option[String]) = new PoolInfo(
    name    = name,
    size    = parseSize(name, size),
    profile = ProfileInfo.fromConfig(name, prefs, display, binaryLocation))
}

final class PoolInfo(
    val name: String,
    val size: Int,
    val profile: ProfileInfo) {
  override def toString = s"PoolInfo($name, $size, $profile)"
}
