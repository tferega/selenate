package net.selenate.server

import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.util.ContextInitializer
import ch.qos.logback.core.util.StatusPrinter
import org.slf4j.LoggerFactory

object Main extends App with CBase {
  initLog
  Runner.start

  private def initLog() {
    val defaultConfig = loadResourceConfig("server.reference.config")
    val userConfig    = loadFileConfig(configPath)
    val config        = userConfig withFallback defaultConfig

    val logLocation = config.getString("server.locations.log")
    System.setProperty("LOG_LOCATION", logLocation)

    val lc = LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext]
    val ci = new ContextInitializer(lc);
    lc.reset();
    ci.autoConfig();
    StatusPrinter.printInCaseOfErrorsOrWarnings(lc);
  }
}
