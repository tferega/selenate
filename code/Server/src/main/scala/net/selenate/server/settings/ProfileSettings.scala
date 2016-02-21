package net.selenate.server
package settings

import java.io.File
import net.selenate.common.exceptions.SeException

object ProfileSettings {
  private def parseDisplay(display: String): DisplaySettings =
    display.toLowerCase match {
      case "main"      => DisplaySettings.Main
      case "firstfree" => DisplaySettings.FirstFree
      case IsInt(num)  => DisplaySettings.Specific(num)
      case _ => throw new SeException(s"""Error while parsing configuration. Offending entry: server.pool.display. Expected one of ["Main", "FirstFree", num], received "$display".""")
    }

  private def parseBinaryLocation(binaryLocation: String): File = {
    val file = new File(binaryLocation)
    if (!file.exists)     throw new SeException(s"""Error while parsing configuration. Offending entry: server.pool.binary. Path "$binaryLocation" does not exist.""")
    if (!file.isFile)     throw new SeException(s"""Error while parsing configuration. Offending entry: server.pool.binary. Path "$binaryLocation" is not a file.""")
    if (!file.canExecute) throw new SeException(s"""Error while parsing configuration. Offending entry: server.pool.binary. File "$binaryLocation" is not executable.""")
    file
  }

  def parsePrefs(prefs: Map[String, String]): Map[String, AnyRef] =
    prefs.mapValues { entry =>
      entry match {
        case IsBoolean(bool) => bool
        case IsInteger(int)  => int
        case IsString(str)   => str
        case _ => throw new SeException(s"""Error while parsing configuration. Offending entry: server.pool.prefs.$entry. Malformed entry.""")
      }
    }

  def fromConfig(
      display: String,
      binaryLocation: Option[String],
      prefs: Map[String, String]) = new ProfileSettings(
    display        = parseDisplay(display),
    binaryLocation = binaryLocation map parseBinaryLocation,
    prefMap        = parsePrefs(prefs))
}


case class ProfileSettings(
    val display: DisplaySettings,
    val binaryLocation: Option[File],
    val prefMap: Map[String, AnyRef])
