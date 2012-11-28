package net.selenate

import java.io.{ PrintWriter, StringWriter }
import java.{ util => ju }

package object server {
  type PF[A, R] = PartialFunction[A, R]

  def ??? = throw new Exception("???: Not implemented")

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