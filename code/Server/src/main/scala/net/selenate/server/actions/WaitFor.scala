package net.selenate.server
package actions

trait WaitFor { self: Loggable =>
  private val timeout    = 20000
  private val resolution = 250

  def waitForPredicate[T](event: => Option[T]): Option[T] = waitFor(event, timeout, resolution)
}
