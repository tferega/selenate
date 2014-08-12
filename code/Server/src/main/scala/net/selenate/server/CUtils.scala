package net.selenate.server

import java.io.File
import com.typesafe.config.ConfigParseOptions
import com.typesafe.config.ConfigSyntax
import com.typesafe.config.ConfigFactory
import scala.collection.JavaConversions._
import com.typesafe.config.Config

private object CUtils {
  def configFile() = {
    val userHome = sys.props.get("user.home").getOrElse(throw new RuntimeException("""Key "user.home" not defined in system properties!"""))
    val branch = sys.props.get("Selenate.branch")
    branch match {
      case Some(b) => new File(userHome + s"/.config/selenate_$branch/server.config");
      case None    => new File(userHome + "/.config/selenate/server.config");
    }
  }

  val parseOpts = ConfigParseOptions
      .defaults()
      .setAllowMissing(false)
      .setSyntax(ConfigSyntax.CONF);

  def loadConfig(name: String) = ConfigFactory.parseResources(name, parseOpts);
  def loadConfig(path: File)   = ConfigFactory.parseFile(path, parseOpts);

  def getKeySet(config: Config) = config.entrySet.map(_.getKey().takeWhile(_ != '.'))
}
