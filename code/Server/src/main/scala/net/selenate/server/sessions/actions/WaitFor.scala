package net.selenate.server.sessions.actions

import scala.annotation.tailrec

trait WaitFor {
  private val timeout    = 25000
  private val resolution = 250

  def waitForPredicate[T](predicate: => Option[T]): Option[T] = {
    @tailrec
    def waitForDoit(end: Long, resolution: Long, predicate: => Option[T]): Option[T] = {
      val current = System.currentTimeMillis
      val remaining = end - current

      if (remaining < 0) {
        None  // Timeout
      } else {
        val p = predicate
        if (p.isDefined) {
          p  // Predicate evaluated to true
        } else {
          // Do not oversleep.
          val sleep = scala.math.min(resolution, remaining)
          Thread.sleep(sleep)
          waitForDoit(end, resolution, predicate)
        }
      }
    }

    val end = System.currentTimeMillis + timeout
    waitForDoit(end, resolution, predicate)
  }

}