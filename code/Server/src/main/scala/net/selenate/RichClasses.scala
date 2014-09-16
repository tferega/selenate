package net.selenate

import java.io.{ File, PrintWriter, StringWriter }
import java.util.Optional

trait RichClasses {
  implicit class RichThrowable_Selenate(base: Exception) {
    def stackTrace: String = {
      val sw = new StringWriter
      val pw = new PrintWriter(sw)
      base.printStackTrace(pw)
      val s = sw.toString
      pw.close
      sw.close
      s
    }
  }

  implicit class RichString_Selenate(base: String) {
    def file: File = new File(base)
  }

  implicit class RichFile_Selenate(base: File) {
    def /(r: String) = new File(base, r)
  }

  implicit class RichOptional_Selenate[T](base: Optional[T]) {
    def toOption: Option[T] = {
      if (base.isPresent) {
        Some(base.get)
      } else {
        None
      }
    }
  }

  implicit class RichIterator_Selenate[T](base: Iterator[T]) {
    def force = base.toIndexedSeq
  }
}
