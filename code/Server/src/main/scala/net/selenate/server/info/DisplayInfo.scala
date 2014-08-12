package net.selenate.server
package info

sealed trait DisplayInfo
object DisplayInfo {
  case object Main      extends DisplayInfo
  case object FirstFree extends DisplayInfo
  case class Specific(num: Int) extends DisplayInfo
}
