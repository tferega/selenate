package net.selenate.server

import org.slf4j.LoggerFactory

trait Loggable {
  lazy val clazz: Class[_] = this.getClass
  lazy val logPrefix: Option[String] = None

  lazy val logger = LoggerFactory.getLogger(clazz)


  private def p(s: String) =
    logPrefix.map("[%s] => " format _).getOrElse("") + s

  protected def logError(s: => String) = logger.error(p(s))
  protected def logWarn(s:  => String) = logger.warn (p(s))
  protected def logInfo(s:  => String) = logger.info (p(s))
  protected def logDebug(s: => String) = logger.debug(p(s))
  protected def logTrace(s: => String) = logger.trace(p(s))

  protected def logError(s: => String, t: => Throwable) = logger.error(p(s), t)
  protected def logWarn(s:  => String, t: => Throwable) = logger.warn (p(s), t)
  protected def logInfo(s:  => String, t: => Throwable) = logger.info (p(s), t)
  protected def logDebug(s: => String, t: => Throwable) = logger.debug(p(s), t)
  protected def logTrace(s: => String, t: => Throwable) = logger.trace(p(s), t)
}
