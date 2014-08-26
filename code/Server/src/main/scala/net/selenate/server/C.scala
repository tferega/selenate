package net.selenate.server

import settings.PoolSettings

import com.typesafe.config.ConfigValueFactory
import scala.collection.JavaConversions._

object C extends Loggable {
  private val defaultConfig = CUtils.loadResourceConfig("server.reference.config")
  private val userConfig    = CUtils.loadFileConfig(CUtils.configPath)
  private val config        = userConfig withFallback defaultConfig

  logTrace(s"""Final configuration content: $config""")

  val branch = CUtils.branch
  val osName = sys.props("os.name")

  object Server {
    private val serverConfig = config.getConfig("server")

    val host = serverConfig.getString("host")

    object Pool {
      private val poolConfig     = serverConfig.getConfig("pool")
      private val size           = poolConfig.getString("size")
      private val prefs          = poolConfig.getObject("prefs").unwrapped.toMap.mapValues(_.toString)
      private val display        = poolConfig.getString("display")
      private val binaryLocation = if (poolConfig.hasPath("binary")) Some(poolConfig.getString("binary")) else None

      val poolInfo = PoolSettings.fromConfig(size, prefs, display, binaryLocation)
    }
  }

  object Akka {
    private val hostnameVal = ConfigValueFactory.fromAnyRef(Server.host)
    val config = CUtils.loadResourceConfig("akka.config").withValue("akka.remote.netty.tcp.hostname", hostnameVal)

    logTrace(s"""Akka configuration content: $config""")
  }
}
