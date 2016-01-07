package net.selenate.server

import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.util.ContextInitializer
import ch.qos.logback.core.util.StatusPrinter
import org.slf4j.LoggerFactory

object EntryPoint extends App with CBase {
  initLog
  Runner.start

  def initLog() {
    val logLocation = C.Server.Locations.LOG
    System.setProperty("LOG_LOCATION", logLocation)

    val lc = LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext]
    val ci = new ContextInitializer(lc);
    lc.reset();
    ci.autoConfig();
    StatusPrinter.printInCaseOfErrorsOrWarnings(lc);
  }
}
