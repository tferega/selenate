package net.selenate.server
package settings

import java.io.File

object ProfileSettings {
  private def parseDisplay(display: String): DisplaySettings =
    display.toLowerCase match {
      case "main"      => DisplaySettings.Main
      case "firstfree" => DisplaySettings.FirstFree
      case IsInt(num)  => DisplaySettings.Specific(num)
      case _ => throw new IllegalArgumentException(s"""Error while parsing configuration. Offending entry: server.pool.display. Expected one of ["Main", "FirstFree", num], received "$display".""")
    }

  private def parseRecord(record: String): Boolean =
    record match {
      case IsBoolean(bool) => bool
      case _ => throw new IllegalArgumentException(s"""Error while parsing configuration. Offending entry: server.pool.record. Expected one of ["True", "False"], received "$record".""")
    }

  private def parseBinaryLocation(binaryLocation: String): File = {
    val file = new File(binaryLocation)
    if (!file.exists)     throw new IllegalArgumentException(s"""Error while parsing configuration. Offending entry: server.pool.binary. Path "$binaryLocation" does not exist.""")
    if (!file.isFile)     throw new IllegalArgumentException(s"""Error while parsing configuration. Offending entry: server.pool.binary. Path "$binaryLocation" is not a file.""")
    if (!file.canExecute) throw new IllegalArgumentException(s"""Error while parsing configuration. Offending entry: server.pool.binary. File "$binaryLocation" is not executable.""")
    file
  }

  private def parsePrefs(prefs: Map[String, String]): Map[String, AnyRef] =
    prefs.mapValues { entry =>
      entry match {
        case IsBoolean(bool) => bool
        case IsInteger(int)  => int
        case IsString(str)   => str
        case _ => throw new IllegalArgumentException(s"""Error while parsing configuration. Offending entry: server.pool.prefs.$entry. Malformed entry.""")
      }
    }

  def fromConfig(
      display: String,
      record: String,
      binaryLocation: Option[String],
      prefs: Map[String, String]) = new ProfileSettings(
    display        = parseDisplay(display),
    record         = parseRecord(record),
    binaryLocation = binaryLocation map parseBinaryLocation,
    prefMap        = parsePrefs(prefs))
}


case class ProfileSettings(
    val display: DisplaySettings,
    val record: Boolean,
    val binaryLocation: Option[File],
    val prefMap: Map[String, AnyRef])
