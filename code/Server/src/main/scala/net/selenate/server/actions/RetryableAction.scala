package net.selenate.server
package actions

import net.selenate.common.comms.req.SeCommsReq
import net.selenate.common.comms.res.SeCommsRes
import net.selenate.common.exceptions.SeActionException

object RetryableAction {
  val maxAttempts = 3
}

trait RetryableAction[A <: SeCommsReq, R <: SeCommsRes] extends Action[A, R] {
  import RetryableAction._

  final def doAct: (A) => R = { arg =>
    def innerDoAct(attempt: Int, exceptionLog: IndexedSeq[Exception]): R = {
      try {
        logTrace(s"Retryable action making attempt No $attempt with argument: $arg")
        val r = retryableAct(arg)
        r
      } catch {
        case e: Exception =>
          val newExceptionLog = exceptionLog :+ e
          if (attempt >= maxAttempts) {
            val report = exceptionListReport(exceptionLog)
            val msg = s"maximum number of attempts ($maxAttempts) exceeded in retryable action! Error list:\n$report"
            logError(msg)
            throw new SeActionException(name, arg, msg)
          } else {
            logWarn(s"Attempt No $attempt in retryable action failed!", e)
            Thread.sleep(1000)
            innerDoAct(attempt + 1, newExceptionLog)
          }
      }
    }

    innerDoAct(1, IndexedSeq.empty[Exception])
  }

  private def exceptionListReport(eList: IndexedSeq[Exception]) =
    eList.map(_.stackTrace).mkString("\n\n" + "-"*50 + "\n\n")

  protected def retryableAct: (A) => R
}
