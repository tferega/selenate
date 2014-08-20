package net.selenate.server

import com.typesafe.config.{ Config, ConfigFactory, ConfigParseOptions, ConfigSyntax }
import java.io.File

private object CUtils {
  private val log = Log(this.getClass)

  val branch = sys.props.get("Selenate.branch")
  val userHome = sys.props.get("user.home").getOrElse(throw new RuntimeException("""Key "user.home" not defined in system properties!"""))
  val configPath = {
    branch match {
      case Some(b) => new File(userHome + s"/.props/selenate_$branch/server.config");
      case None    => new File(userHome + "/.props/selenate/server.config");
    }
  }

  log.trace(s"""Detected branch: $branch""")
  log.trace(s"""Detected user home: $userHome""")
  log.trace(s"""Detected confing path: $configPath""")

  def parseOpts(allowMissing: Boolean) = ConfigParseOptions
      .defaults()
      .setAllowMissing(allowMissing)
      .setSyntax(ConfigSyntax.CONF);

  def loadResourceConfig(name: String): Config = {
    try {
      val config = ConfigFactory.parseResources(name, parseOpts(false))
      log.debug(s"""Successfully loaded config resource "$name"""")
      log.trace(s"""Config resource "$name" content: ${ config.toString } }""")
      config
    } catch {
      case e: Exception =>
        val msg = s"""An error occured while loading config resource "$name"!"""
        log.error(msg, e)
        throw new RuntimeException(msg, e)
    }
  }

  def loadFileConfig(path: File): Config = {
    try {
      val config = ConfigFactory.parseFile(path, parseOpts(true))
      log.debug(s"""Successfully loaded config file "$path"""")
      log.trace(s"""Config file "$path" content: $config }""")
      config
    } catch {
      case e: Exception =>
        val msg = s"""An error occured while loading config file "$path"!"""
        log.error(msg, e)
        throw new RuntimeException(msg, e)
    }
  }
}
