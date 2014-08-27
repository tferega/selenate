package net.selenate.server
package sessions.actions

object RetryableAction {
  val maxAttempts = 3
}

trait RetryableAction[A, R] extends Action[A, R] {
  import RetryableAction._

  final def act: (A) => R = { arg =>
    def doAct(attempt: Int, exceptionLog: IndexedSeq[Exception]): R = {
      try {
        logTrace(s"Retryable action making attempt No $attempt with argument: $arg")
        val r = retryableAct(arg)
        r
      } catch {
        case e: Exception =>
          val newExceptionLog = exceptionLog :+ e
          if (attempt >= maxAttempts) {
            val report = exceptionListReport(exceptionLog)
            val msg = s"Maximum number of attempts ($maxAttempts) exceeded in retryable action! Error list:\n$report"
            logError(msg)
            throw new Exception(msg)
          } else {
            logWarn(s"Attempt No $attempt in retryable action failed!", e)
            Thread.sleep(1000)
            doAct(attempt + 1, newExceptionLog)
          }
      }
    }

    doAct(1, IndexedSeq.empty[Exception])
  }

  private def exceptionListReport(eList: IndexedSeq[Exception]) =
    eList.map(_.stackTrace).mkString("\n\n" + "-"*50 + "\n\n")

  protected def retryableAct: (A) => R
}
