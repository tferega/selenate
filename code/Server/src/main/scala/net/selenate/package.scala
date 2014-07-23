package net.selenate

import java.io.{ File, PrintWriter, StringWriter }
import java.{ util => ju }
import scala.concurrent.ExecutionContext
import net.selenate.server.Log

package object server {
  private val log = Log(this.getClass)
  type PF[A, R] = PartialFunction[A, R]

  implicit val ec = ExecutionContext.fromExecutor(java.util.concurrent.Executors.newCachedThreadPool())

  implicit class RichException(e: Exception) {
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

  implicit class RichString(s: String) {
    def file: File = new File(s)
  }

  implicit class RichFile(l: File) {
    def /(r: String) = new File(l, r)
  }

  def tryo[T](f: => T): Option[T] =
    try {
      Some(f)
    } catch {
      case e: Exception =>
        log.trace("Error in tryo: ", e)
        None
    }

  def tryb[T](f: => T): Boolean =
    try {
      f
      true
    } catch {
      case e: Exception => false
    }

  object IsString {
    private val R = """'(.*)'"""r
    def unapply(raw: String): Option[java.lang.String] =
      tryo {
        val R(extracted) = raw
        extracted
      }
  }

  object IsInteger {
    def unapply(raw: String): Option[java.lang.Integer] = tryo(raw.toInt)
  }

  object IsInt {
    def unapply(raw: String): Option[Int] = tryo(raw.toInt)
  }

  object IsBoolean {
    def unapply(raw: String): Option[java.lang.Boolean] = tryo(raw.toBoolean)
  }

  object IsBool {
    def unapply(raw: String): Option[Boolean] = tryo(raw.toBoolean)
  }

  object IsFile {
    def unapply(raw: String): Option[File] = tryo(new File(raw))
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

  def mapToRealJava[K, V](in: Map[K, V]): ju.Map[K, V] = {
    val out = new ju.HashMap[K, V]
    in foreach { case(k, v) => out.put(k, v) }
    out
  }

  def toInteger(i: Int) = java.lang.Integer.valueOf(i)
}
