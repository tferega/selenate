package net.selenate.server
package actions

trait WaitFor { self: Loggable =>
  protected def timeout: Long
  protected def resolution: Long
  protected def delay: Long

  def waitForPredicate[T](event: => Option[T]): Option[T] = waitFor(event, timeout, resolution, delay)
}
