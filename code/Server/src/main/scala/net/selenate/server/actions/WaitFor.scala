package net.selenate.server
package actions

object WaitFor {
  private val timeout    = 20000
  private val resolution = 250
  private val delay      = 500
}

trait WaitFor { self: Loggable =>
  import WaitFor._
  def waitForPredicate[T](event: => Option[T]): Option[T] = waitFor(event, timeout, resolution, delay)
}
