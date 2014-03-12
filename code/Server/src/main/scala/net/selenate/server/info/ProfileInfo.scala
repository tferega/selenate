package net.selenate.server
package info

import java.io.File

object ProfileInfo {
  private val DefaultPrefMap        = Map.empty[String, AnyRef]
  private val DefaultDisplay        = DisplayInfo.Main
  private val DefaultBinaryLocation = None

  private def parsePrefs(name: String)(prefs: String): Map[String, AnyRef] =
    prefs.split(";").map { entry =>
      entry.split("=").toList match {
        case ""  :: _  :: Nil => throw new IllegalArgumentException(s"""Error while parsing configuration. Offending entry: server.pool.$name.prefs($entry). Key not defined.""")
        case key :: "" :: Nil => throw new IllegalArgumentException(s"""Error while parsing configuration. Offending entry: server.pool.$name.prefs($entry). Value not defined.""")
        case key :: IsBoolean(value) :: Nil => key -> value
        case key :: IsInteger(value) :: Nil => key -> value
        case key :: IsString(value)  :: Nil => key -> value
        case _ => throw new IllegalArgumentException(s"""Error while parsing configuration. Offending entry: server.pool.$name.prefs($entry). Malformed entry.""")
      }
  }.toMap

  private def parseDisplay(name: String)(display: String): DisplayInfo =
    display.toLowerCase match {
      case "main"      => DisplayInfo.Main
      case "firstfree" => DisplayInfo.FirstFree
      case IsInt(num)  => DisplayInfo.Specific(num)
      case _ => throw new IllegalArgumentException(s"""Error while parsing configuration. Offending entry: server.pool.$name.display. Expected one of ["Main", "FirstFree"], received "$display".""")
    }

  private def parseBinaryLocation(name: String)(binaryLocation: String): File = {
    val file = new File(binaryLocation)
    if (!file.exists)     throw new IllegalArgumentException(s"""Error while parsing configuration. Offending entry: server.pool.$name.binary-location. Path "$binaryLocation" does not exist.""")
    if (!file.isFile)     throw new IllegalArgumentException(s"""Error while parsing configuration. Offending entry: server.pool.$name.binary-location. Path "$binaryLocation" is not a file.""")
    if (!file.canExecute) throw new IllegalArgumentException(s"""Error while parsing configuration. Offending entry: server.pool.$name.binary-location. File "$binaryLocation" is not executable.""")
    file
  }

  def empty = new ProfileInfo(
      prefMap        = DefaultPrefMap,
      display        = DefaultDisplay,
      binaryLocation = DefaultBinaryLocation)

  def fromConfig(
      name: String,
      prefs: Option[String],
      display: Option[String],
      binaryLocation: Option[String]) = new ProfileInfo(
    prefMap        = prefs.map(parsePrefs(name)).getOrElse(DefaultPrefMap),
    display        = display.map(parseDisplay(name)).getOrElse(DefaultDisplay),
    binaryLocation = binaryLocation.map(parseBinaryLocation(name)))
}


final class ProfileInfo(
    val prefMap: Map[String, AnyRef] = ProfileInfo.DefaultPrefMap,
    val display: DisplayInfo         = ProfileInfo.DefaultDisplay,
    val binaryLocation: Option[File] = ProfileInfo.DefaultBinaryLocation) {
  def ===(that: ProfileInfo): Boolean =
    this.prefMap == that.prefMap &&
    this.display == that.display &&
    this.binaryLocation == that.binaryLocation
}
