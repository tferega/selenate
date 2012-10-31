package net.selenate

package object server {
  def ??? : Nothing = throw new Exception("Not implemented")

  def tryb[T](f: => T): Boolean =
    try {
      f
      true
    } catch {
      case e: Exception =>
        false
    }
}