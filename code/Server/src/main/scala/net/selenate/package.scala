package net.selenate

import server.Loggable

import java.{ util => ju }
import scala.concurrent.ExecutionContext

package object server
    extends RichClasses
    with Extractors with Loggable {
  type PF[A, R] = PartialFunction[A, R]

  implicit val ec = ExecutionContext.fromExecutor(ju.concurrent.Executors.newCachedThreadPool())

  def tryo[T](f: => T): Option[T] =
    try {
      Some(f)
    } catch {
      case e: Exception =>
        if (C.Server.TRYO_TRACE) logTrace("Error in tryo", e)
        None
    }

  def tryb[T](f: => T): Boolean =
    try {
      f
      true
    } catch {
      case e: Exception =>
        if (C.Server.TRYO_TRACE) logTrace("Error in tryb", e)
        false
    }

  // scala.collection.JavaConversions and scala.collection.JavaConverters are not adequate.
  // They create instances of scala.collection.JavaConversions$SeqWrapper (or similar), which subclass
  // java lists.
  // That fails when pure java tries to de-serialize it.
  def seqToRealJava[T](in: Seq[T]): ju.List[T] = {
    val out = new ju.ArrayList[T]()
    in foreach out.add
    out
  }

  def setToRealJava[T](in: Set[T]): ju.Set[T] = {
    val out = new ju.HashSet[T]()
    in foreach out.add
    out
  }

  def mapToRealJava[K, V](in: Map[K, V]): ju.Map[K, V] = {
    val out = new ju.HashMap[K, V]
    in foreach { case(k, v) => out.put(k, v) }
    out
  }

  def toInteger(i: Int) = java.lang.Integer.valueOf(i)

  import scala.annotation.tailrec
  def waitFor[T](
      event: => Option[T],
      timeout: Long,
      resolution: Long,
      delay: Long): Option[T] = {
    @tailrec
    def waitForDoit(end: Long): Option[T] = {
      val current = System.currentTimeMillis
      val remaining = end - current

      if (remaining < 0) {
        logTrace("WaitFor failed to meet the predicate")
        None  // Timeout
      } else {
        val pPrelim = event
        if (pPrelim.isDefined) {
          logTrace("WaitFor preliminary met the predicate")
          if (delay > 0) {
            logTrace("WaitFor waiting for delay")
            Thread.sleep(delay)
            val pFinal = event
            if (pFinal.isDefined) {
              logTrace("WaitFor successfully returning the predicate")
              pFinal
            } else {
              logTrace("WaitFor predicate disappeared after delay; continuing waiting...")
              waitForDoit(end);
            }
          } else {
            logTrace("WaitFor successfully returning the predicate")
            pPrelim  // Predicate evaluated to true
          }
        } else {
          // Do not oversleep.
          val sleep = scala.math.min(resolution, remaining)
          logTrace(s"WaitFor sleeping for $sleep ms")
          Thread.sleep(sleep)
          waitForDoit(end)
        }
      }
    }

    logTrace(s"Starting WaitFor with timeout $timeout of ms, resolution of $resolution ms, and a delay of $delay ms")
    val end = System.currentTimeMillis + timeout
    waitForDoit(end)
  }
}
