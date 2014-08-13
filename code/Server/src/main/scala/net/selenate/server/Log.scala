package net.selenate.server

import org.slf4j.LoggerFactory

object Log {
  def apply[T](clazz: Class[T]) = new Log(clazz)
}

class Log[T] private (clazz: Class[T]) {
  val logger = LoggerFactory.getLogger(clazz)

  def error(s: => String) = logger.error(s)
  def warn(s:  => String) = logger.warn(s)
  def info(s:  => String) = logger.info(s)
  def debug(s: => String) = logger.debug(s)
  def trace(s: => String) = logger.trace(s)

  def error(s: => String, t: => Throwable) = logger.error(s, t)
  def warn(s:  => String, t: => Throwable) = logger.warn( s, t)
  def info(s:  => String, t: => Throwable) = logger.info( s, t)
  def debug(s: => String, t: => Throwable) = logger.debug(s, t)
  def trace(s: => String, t: => Throwable) = logger.trace(s, t)
}
