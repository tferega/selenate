package net.selenate.server
package actions

import scala.annotation.tailrec

trait WaitFor { self: Loggable =>
  private val timeout    = 20000
  private val resolution = 250

  def waitForPredicate[T](predicate: => Option[T]): Option[T] = {
    @tailrec
    def waitForDoit(end: Long, resolution: Long, predicate: => Option[T]): Option[T] = {
      val current = System.currentTimeMillis
      val remaining = end - current

      if (remaining < 0) {
        logTrace("WaitFor failed to meet the predicate")
        None  // Timeout
      } else {
        val p = predicate
        if (p.isDefined) {
          logTrace("WaitFor successfully met the predicate")
          p  // Predicate evaluated to true
        } else {
          // Do not oversleep.
          val sleep = scala.math.min(resolution, remaining)
          logTrace(s"WaitFor sleeping for $sleep ms")
          Thread.sleep(sleep)
          waitForDoit(end, resolution, predicate)
        }
      }
    }

    logTrace(s"Starting WaitFor with timeout $timeout ms and resolution $resolution ms")
    val end = System.currentTimeMillis + timeout
    waitForDoit(end, resolution, predicate)
  }
}
