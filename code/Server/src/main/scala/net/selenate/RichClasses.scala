package net.selenate

import java.io.{ PrintWriter, StringWriter }

trait RichClasses {
  implicit class ImpaleException(e: Exception) {
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

  implicit class ImpaleString(l: String) {
    def /(r: String): String = "%s/%s" format(l, r)
  }
}
