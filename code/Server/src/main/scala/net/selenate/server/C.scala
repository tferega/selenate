package net.selenate.server

import info.PoolInfo

import org.streum.configrity.Configuration
import scala.io.Source

object C {
  private val configFile = sys.props("user.home").file / ".config" / "selenate" / "server.config"
  private val config = Configuration.load(Source.fromFile(configFile))

  object Server {
    private val serverConfig = config.detach ("server")

    object Pool {
      private val poolConfig = serverConfig.detach("pool")

      private def getPoolInfo(p: (String, Configuration)) = {
        val name = p._1
        val config = p._2

        val size           = config.get[String]("size")
        val prefs          = config.get[String]("prefs")
        val display        = config.get[String]("display")
        val binaryLocation = config.get[String]("binary-location")
        PoolInfo.fromConfig(name, size, prefs, display, binaryLocation)
      }

      val poolInfoList = poolConfig.detachAll map getPoolInfo
    }
  }
}
