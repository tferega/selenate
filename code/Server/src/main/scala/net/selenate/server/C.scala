package net.selenate.server

import settings.PoolSettings

import com.typesafe.config.{ Config, ConfigValueFactory }
import scala.collection.JavaConversions._

class ConfigSection(section: String)(implicit baseConfig: Config) {
  protected val config = baseConfig.getConfig(section)
}

object C extends Loggable {
  private val defaultConfig = CUtils.loadResourceConfig("server.reference.config")
  private val userConfig    = CUtils.loadFileConfig(CUtils.configPath)
  private implicit val baseConfig = userConfig withFallback defaultConfig

  logTrace(s"""Final configuration content: $baseConfig""")

  val BRANCH = CUtils.branch
  val OS_NAME = sys.props("os.name")

  object Server extends ConfigSection("server") {
    val HOST = config.getString("host")

    object Locations extends ConfigSection("server.locations") {
      val RECORDINGS = config.getString("recordings")
    }

    object Timeouts extends ConfigSection("server.timeouts") {
      val PAGE_LOAD = config.getInt("page_load")
      val SHUTDOWN  = config.getInt("shutdown")
    }


    object Pool extends ConfigSection("server.pool") {
      val SIZE    = config.getString("size")
      val DISPLAY = config.getString("display")
      val RECORD  = config.getString("record")
      val BINARY  = if (config.hasPath("binary")) Some(config.getString("binary")) else None
      val PREFS   = config.getObject("prefs").unwrapped.toMap.mapValues(_.toString)
    }
  }

  object Akka {
    private val hostnameVal = ConfigValueFactory.fromAnyRef(Server.HOST)
    val CONFIG = CUtils.loadResourceConfig("akka.config").withValue("akka.remote.netty.tcp.hostname", hostnameVal)

    logTrace(s"""Akka configuration content: $CONFIG""")
  }
}
