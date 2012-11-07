package net.selenate

import java.io.{ PrintWriter, StringWriter }

package object server {
  // My deepest apologies for this, but I .. could .. not .. resist.
  // Also, it is quite self-explanatory :)
  def tryOrElse[E, T](elseFun: (Exception) => E): ( => T) => Either[E, T] = (tryFun) => {
    try {
      Right(tryFun)
    } catch {
      case e: Exception =>
        Left(elseFun(e))
    }
  }

  implicit def exceptionImpaler(e: Exception): ImpaleException = new ImpaleException(e)
  class ImpaleException(e: Exception) {
    def stackTrace: String = {
      val sw = new StringWriter
      val pw = new PrintWriter(sw)
      e.printStackTrace(pw)
      val s = sw.toString
      pw.close
      sw.close
      s
    }
  }
}