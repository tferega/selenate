package net.selenate

import java.io.{ File, PrintWriter, StringWriter }

trait RichClasses {
  implicit class RichExceptionSelenate(e: Exception) {
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

  implicit class RichStringSelenate(s: String) {
    def file: File = new File(s)
  }

  implicit class RichFileSelenate(l: File) {
    def /(r: String) = new File(l, r)
  }
}
