package net.selenate

import java.io.{ PrintWriter, StringWriter }
import java.{ util => ju }

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
}