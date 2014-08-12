package net.selenate.server
package info

import java.io.File

object ProfileInfo {
  private def parsePrefs(name: String, prefs: Map[String, String]): Map[String, AnyRef] =
    prefs.mapValues { entry =>
      entry match {
        case IsBoolean(bool) => bool
        case IsInteger(int)  => int
        case IsString(str)   => str
        case _ => throw new IllegalArgumentException(s"""Error while parsing configuration. Offending entry: server.pool.$name.prefs($entry). Malformed entry.""")
      }
    }

  private def parseDisplay(name: String, display: String): DisplayInfo =
    display.toLowerCase match {
      case "main"      => DisplayInfo.Main
      case "firstfree" => DisplayInfo.FirstFree
      case IsInt(num)  => DisplayInfo.Specific(num)
      case _ => throw new IllegalArgumentException(s"""Error while parsing configuration. Offending entry: server.pool.$name.display. Expected one of ["Main", "FirstFree", num], received "$display".""")
    }

  private def parseBinaryLocation(name: String)(binaryLocation: String): File = {
    val file = new File(binaryLocation)
    if (!file.exists)     throw new IllegalArgumentException(s"""Error while parsing configuration. Offending entry: server.pool.$name.binary-location. Path "$binaryLocation" does not exist.""")
    if (!file.isFile)     throw new IllegalArgumentException(s"""Error while parsing configuration. Offending entry: server.pool.$name.binary-location. Path "$binaryLocation" is not a file.""")
    if (!file.canExecute) throw new IllegalArgumentException(s"""Error while parsing configuration. Offending entry: server.pool.$name.binary-location. File "$binaryLocation" is not executable.""")
    file
  }

  def default = PoolInfo.default.profile

  def fromConfig(
      name: String,
      prefs: Map[String, String],
      display: String,
      binaryLocation: Option[String]) = new ProfileInfo(
    prefMap        = parsePrefs(name, prefs),
    display        = parseDisplay(name, display),
    binaryLocation = binaryLocation map parseBinaryLocation(name))
}


final class ProfileInfo(
    val prefMap: Map[String, AnyRef],
    val display: DisplayInfo,
    val binaryLocation: Option[File]) {
  def ===(that: ProfileInfo): Boolean =
    this.prefMap == that.prefMap &&
    this.display == that.display &&
    this.binaryLocation == that.binaryLocation

  override def toString = s"ProfileInfo($prefMap, $display, $binaryLocation)"
}
