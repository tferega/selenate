package net.selenate.server

import com.typesafe.config.{ Config, ConfigFactory, ConfigParseOptions, ConfigSyntax }
import java.io.File
import net.selenate.common.exceptions.SeException

trait CBase {
  val configOverride = sys.props.get("Selenate.config_override")
  val branch         = sys.props.get("Selenate.branch")
  val userHome       = sys.props.get("user.home").getOrElse(throw new SeException("""Key "user.home" not defined in system properties!"""))
  val configPath = {
    (configOverride, branch) match {
      case (Some(c), _)    => new File(c)
      case (None, Some(b)) => new File(userHome + s"/.config/selenate_$b/")
      case (None, None)    => new File(userHome + s"/.config/selenate/")
    }
  }

  def parseOpts(allowMissing: Boolean) = ConfigParseOptions
      .defaults()
      .setAllowMissing(allowMissing)
      .setSyntax(ConfigSyntax.CONF);

  def loadResourceConfig(resource: String): Config =
    ConfigFactory.parseResources(resource, parseOpts(false))

  def loadFileConfig(path: File): Config =
    ConfigFactory.parseFile(path, parseOpts(true))
}

trait CUtils extends CBase with Loggable {
  logTrace(s"""Detected confing override: $configOverride""")
  logTrace(s"""Detected branch: $branch""")
  logTrace(s"""Detected user home: $userHome""")
  logTrace(s"""Detected confing path: $configPath""")

  private def loadResourceConfig(name: String, resource: String): Config = {
    try {
      logDebug(s"""Loading $name resource config from: "$resource"""")
      val config = loadResourceConfig(resource)
      logTrace(s"""Content of $name resource config "$resource": ${ config.toString }""")
      config
    } catch {
      case e: Exception =>
        val msg = s"""An error occured while loading $name resource config "$resource"!"""
        logError(msg, e)
        throw new SeException(msg, e)
    }
  }

  private def loadFileConfig(name: String, filename: String): Config = {
    val path = configPath / filename
    try {
      logDebug(s"""Loading $name file config from: "$path"""")
      val config = loadFileConfig(path)
      logTrace(s"""Content of $name file config "$path": ${ config.toString }""")
      config
    } catch {
      case e: Exception =>
        val msg = s"""An error occured while loading $name file config "$path"!"""
        logError(msg, e)
        throw new SeException(msg, e)
    }
  }

  def loadAppUser       = loadFileConfig("user application", "server.config")
  def loadAppReference  = loadResourceConfig("reference application", "server.reference.config")
  def loadAkkaUser      = loadFileConfig("user akka", "server-akka.config")
  def loadAkkaReference = loadResourceConfig("reference akka", "selenate-akka.reference.config")
}
