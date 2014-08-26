package net.selenate.server
package settings

import java.io.File

object ProfileSettings {
  private def parsePrefs(prefs: Map[String, String]): Map[String, AnyRef] =
    prefs.mapValues { entry =>
      entry match {
        case IsBoolean(bool) => bool
        case IsInteger(int)  => int
        case IsString(str)   => str
        case _ => throw new IllegalArgumentException(s"""Error while parsing configuration. Offending entry: server.pool.prefs.$entry. Malformed entry.""")
      }
    }

  private def parseDisplay(display: String): DisplaySettings =
    display.toLowerCase match {
      case "main"      => DisplaySettings.Main
      case "firstfree" => DisplaySettings.FirstFree
      case IsInt(num)  => DisplaySettings.Specific(num)
      case _ => throw new IllegalArgumentException(s"""Error while parsing configuration. Offending entry: server.pool.display. Expected one of ["Main", "FirstFree", num], received "$display".""")
    }

  private def parseBinaryLocation(binaryLocation: String): File = {
    val file = new File(binaryLocation)
    if (!file.exists)     throw new IllegalArgumentException(s"""Error while parsing configuration. Offending entry: server.pool.binary. Path "$binaryLocation" does not exist.""")
    if (!file.isFile)     throw new IllegalArgumentException(s"""Error while parsing configuration. Offending entry: server.pool.binary. Path "$binaryLocation" is not a file.""")
    if (!file.canExecute) throw new IllegalArgumentException(s"""Error while parsing configuration. Offending entry: server.pool.binary. File "$binaryLocation" is not executable.""")
    file
  }

  def fromConfig(
      prefs: Map[String, String],
      display: String,
      binaryLocation: Option[String]) = new ProfileSettings(
    prefMap        = parsePrefs(prefs),
    display        = parseDisplay(display),
    binaryLocation = binaryLocation map parseBinaryLocation)
}


case class ProfileSettings(
    val prefMap: Map[String, AnyRef],
    val display: DisplaySettings,
    val binaryLocation: Option[File])
