package net.selenate.server
package sessions
package actions

import java.io.IOException

trait RetryableAction[A, R] extends IAction[A, R] {

  private val MAX_ATTEMPTS = 3

  protected def log: Log[_]


  final def act = { arg =>
    def doInner(attempt: Int, exceptionLog: IndexedSeq[Exception]): R = {
      try {
        log.trace(s"Retryable action making attempt No $attempt with argument: $arg")
        val r = retryableAct(arg)
        r
      } catch {
        case e: Exception =>
          val newExceptionLog = exceptionLog :+ e
          if (attempt >= MAX_ATTEMPTS) {
            val report = exceptionListReport(exceptionLog)
            val msg = s"maximum number of attempts ($MAX_ATTEMPTS) exceeded in retryable action! Error list:\n$report"
            log.error(msg)
            throw new IOException(msg, e)
          } else {
            log.warn(s"Attempt No $attempt in retryable action failed!", e)
            Thread.sleep(1000)
            doInner(attempt + 1, newExceptionLog)
          }
      }
    }

    doInner(1, IndexedSeq.empty[Exception])
  }

  private def exceptionListReport(eList: IndexedSeq[Exception]) =
    eList.map(_.stackTrace).mkString("\n\n" + "-"*50 + "\n\n")


  def retryableAct: (A) => R
}
