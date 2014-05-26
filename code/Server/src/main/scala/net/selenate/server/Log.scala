package net.selenate
package server

import org.slf4j.LoggerFactory

object Log {
  def apply[T](clazz: Class[T]) = new Log(clazz, None)
  def apply[T](clazz: Class[T], prefix: String) = new Log(clazz, Some(prefix))
}

class Log[T] private (clazz: Class[T], prefix: Option[String]) {
  val logger = LoggerFactory.getLogger(clazz)

  private def p(s: String) =
    prefix.map("[%s] => " format _).getOrElse("") + s

  def error(s: => String)                  = logger.error(p(s))
  def error(s: => String, t: => Throwable) = logger.error(p(s), t)
  def warn(s:  => String)                  = logger.warn(p(s))
  def info(s:  => String)                  = logger.info(p(s))
  def debug(s: => String)                  = logger.debug(p(s))
  def trace(s: => String)                  = logger.trace(p(s))
  def trace(s: => String, t: => Throwable) = logger.trace(p(s), t)
}
