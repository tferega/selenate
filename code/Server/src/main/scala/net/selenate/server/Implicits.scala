//package net.selenate
//package server
//
////implicit def exceptionImpaler(e: Exception): ImpaleException = new ImpaleException(e)
//implicit class ImpaleException(e: Exception) {
//  def stackTrace: String = {
//    val sw = new StringWriter
//    val pw = new PrintWriter(sw)
//    e.printStackTrace(pw)
//    val s = sw.toString
//    pw.close
//    sw.close
//    s
//  }
//}
//
//implicit def strImpaler(l: String) = new {
//  def /(r: String): String = "%s/%s" format(l, r)
//}
