package net.selenate.server

import settings.PoolSettings

import com.typesafe.config.{ Config, ConfigValueFactory }
import scala.collection.JavaConversions._

class ConfigSection(section: String)(implicit baseConfig: Config) {
  protected val config = baseConfig.getConfig(section)
}

object C extends CUtils {
  private val defaultConfig = loadResourceConfig("server.reference.config")
  private val userConfig    = loadFileConfig(configPath)
  private implicit val baseConfig = userConfig withFallback defaultConfig

  logTrace(s"""Final configuration content: $baseConfig""")

  val BRANCH = branch
  val OS_NAME = sys.props("os.name")

  object Server extends ConfigSection("server") {
    val HOST        = config.getString("host")
    val PORT        = config.getString("port")
    val IS_KILLABLE = config.getBoolean("is_killable")

    object Locations extends ConfigSection("server.locations") {
      val RECORDINGS = config.getString("recordings")
    }

    object Timeouts extends ConfigSection("server.timeouts") {
      val PAGE_LOAD = config.getInt("page_load")
      val SHUTDOWN  = config.getInt("shutdown")
    }


    object Pool extends ConfigSection("server.pool") {
      val SIZE           = config.getString("size")
      val DISPLAY        = config.getString("display")
      val DISPLAY_WIDTH  = config.getString("display-width")
      val DISPLAY_HEIGHT = config.getString("display-height")
      val VNC_HOST       = config.getString("vnc-host")
      val BINARY         = if (config.hasPath("binary")) Some(config.getString("binary")) else None
      val PREFS          = config.getObject("prefs").unwrapped.toMap.mapValues(_.toString)
    }
  }

  object Akka {
    private val hostnameVal = ConfigValueFactory.fromAnyRef(Server.HOST)
    private val portVal     = ConfigValueFactory.fromAnyRef(Server.PORT)
    val CONFIG = loadResourceConfig("akka.config")
      .withValue("akka.remote.netty.tcp.hostname", hostnameVal)
      .withValue("akka.remote.netty.tcp.port", portVal)

    logTrace(s"""Akka configuration content: $CONFIG""")
  }
}
