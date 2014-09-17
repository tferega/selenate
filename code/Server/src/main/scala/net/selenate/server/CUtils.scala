package net.selenate.server

import com.typesafe.config.{ Config, ConfigFactory, ConfigParseOptions, ConfigSyntax }
import java.io.File
import net.selenate.common.exceptions.SeException

trait CBase {
  val branch = sys.props.get("Selenate.branch")
  val userHome = sys.props.get("user.home").getOrElse(throw new SeException("""Key "user.home" not defined in system properties!"""))
  val configPath = {
    branch match {
      case Some(b) => new File(userHome + s"/.props/selenate_$branch/server.config");
      case None    => new File(userHome + "/.props/selenate/server.config");
    }
  }

  def parseOpts(allowMissing: Boolean) = ConfigParseOptions
      .defaults()
      .setAllowMissing(allowMissing)
      .setSyntax(ConfigSyntax.CONF);

  def loadResourceConfig(name: String): Config =
    ConfigFactory.parseResources(name, parseOpts(false))

  def loadFileConfig(path: File): Config =
    ConfigFactory.parseFile(path, parseOpts(true))
}

trait CUtils extends CBase with Loggable {
  logTrace(s"""Detected branch: $branch""")
  logTrace(s"""Detected user home: $userHome""")
  logTrace(s"""Detected confing path: $configPath""")

  override def loadResourceConfig(name: String): Config = {
    try {
      val config = super.loadResourceConfig(name)
      logDebug(s"""Successfully loaded config resource "$name"""")
      logTrace(s"""Config resource "$name" content: ${ config.toString } }""")
      config
    } catch {
      case e: Exception =>
        val msg = s"""An error occured while loading config resource "$name"!"""
        logError(msg, e)
        throw new SeException(msg, e)
    }
  }

  override def loadFileConfig(path: File): Config = {
    try {
      val config = super.loadFileConfig(path)
      logDebug(s"""Successfully loaded config file "$path"""")
      logTrace(s"""Config file "$path" content: $config }""")
      config
    } catch {
      case e: Exception =>
        val msg = s"""An error occured while loading config file "$path"!"""
        logError(msg, e)
        throw new SeException(msg, e)
    }
  }
}
