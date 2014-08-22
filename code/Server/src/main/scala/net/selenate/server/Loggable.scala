package net.selenate.server

import org.slf4j.LoggerFactory

trait Loggable {
  lazy val clazz: Class[_] = this.getClass
  lazy val logger = LoggerFactory.getLogger(clazz)

  protected def logError(s: => String) = logger.error(s)
  protected def logWarn(s:  => String) = logger.warn(s)
  protected def logInfo(s:  => String) = logger.info(s)
  protected def logDebug(s: => String) = logger.debug(s)
  protected def logTrace(s: => String) = logger.trace(s)

  protected def logError(s: => String, t: => Throwable) = logger.error(s, t)
  protected def logWarn(s:  => String, t: => Throwable) = logger.warn( s, t)
  protected def logInfo(s:  => String, t: => Throwable) = logger.info( s, t)
  protected def logDebug(s: => String, t: => Throwable) = logger.debug(s, t)
  protected def logTrace(s: => String, t: => Throwable) = logger.trace(s, t)
}
