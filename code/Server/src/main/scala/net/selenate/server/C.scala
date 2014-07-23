package net.selenate.server

import info.PoolInfo

import org.streum.configrity.Configuration
import scala.io.Source

object C {
  val branch             = sys.props("branch")
  private val configPath = sys.props("user.home") + "/.config" + "/selenate/" + branch + "/server.config"
  private val config     = Configuration.load(configPath)

  object Server {
    private val serverConfig = config.detach ("server")
    val poolSize          = serverConfig[Int]("pool-size")
    val host              = serverConfig[String]("host")
    //val defaultProfileOpt = serverConfig.get[String]("default-profile")


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
