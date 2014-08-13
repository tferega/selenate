package net.selenate.server

import java.io.File
import com.typesafe.config.ConfigParseOptions
import com.typesafe.config.ConfigSyntax
import com.typesafe.config.ConfigFactory
import scala.collection.JavaConversions._
import com.typesafe.config.Config

private object CUtils {
  private val log = Log(this.getClass, "Config")

  val branch = sys.props.get("Selenate.branch")

  def configFile() = {
    val userHome = sys.props.get("user.home").getOrElse(throw new RuntimeException("""Key "user.home" not defined in system properties!"""))
    val configFile = branch match {
      case Some(b) => new File(userHome + s"/.config/selenate_$branch/server.config");
      case None    => new File(userHome + "/.config/selenate/server.config");
    }

    log.debug("Config file loaded: " + configFile.toString)
    configFile
  }

  def parseOpts(allowMissing: Boolean) = ConfigParseOptions
      .defaults()
      .setAllowMissing(allowMissing)
      .setSyntax(ConfigSyntax.CONF);

  def loadDefaultConfig(name: String) = ConfigFactory.parseResources(name, parseOpts(false));
  def loadUserConfig(path: File)   = ConfigFactory.parseFile(path, parseOpts(true));

  def getKeySet(config: Config) = config.entrySet.map(_.getKey().takeWhile(_ != '.'))
}
