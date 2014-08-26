package net.selenate.server
package settings

sealed trait DisplaySettings
object DisplaySettings {
  case object Main      extends DisplaySettings
  case object FirstFree extends DisplaySettings
  case class Specific(num: Int) extends DisplaySettings
}
