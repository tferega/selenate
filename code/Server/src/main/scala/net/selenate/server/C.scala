package net.selenate.server

import com.typesafe.config.Config
import net.selenate.server.info.PoolInfo
import scala.collection.JavaConversions._

object C {
  import CUtils._
  private val log = Log(this.getClass)

  private val defaultConfig = CUtils.loadDefaultConfig("server.reference.config")
  private val userConfig    = loadUserConfig(configFile())
  private val config        = userConfig withFallback defaultConfig

  log.trace("Loaded config: " + config.toString)

  val branch = CUtils.branch

  object Server {
    private val serverConfig = config.getConfig("server")

    val host = serverConfig.getString("host")

    object Pool {
      private val poolConfig = serverConfig.getConfig("pool")
      private val defaultPoolEntryConfig = loadDefaultConfig("poolentry.reference.config")
      private val poolNameList = getKeySet(poolConfig).toList

      log.trace("Detected pool names: " + poolNameList.toString)

      def poolInfoList: List[PoolInfo] = poolNameList
          .map(e => e -> poolConfig.getConfig(e))
          .map(getPoolInfo)

      def defaultPool = getPoolInfo(("default", defaultPoolEntryConfig))

      private def getPoolInfo(p: (String, Config)) = {
        val name                = p._1
        val userPoolEntryConfig = p._2
        val poolEntryConfig     = userPoolEntryConfig withFallback defaultPoolEntryConfig

        log.trace(s"Parsing pool $name with config: " + poolEntryConfig)

        val size           = poolEntryConfig.getString("size")
        val prefs          = poolEntryConfig.getObject("prefs").unwrapped.toMap.mapValues(_.toString)
        val display        = poolEntryConfig.getString("display")
        val binaryLocation = if (poolEntryConfig.hasPath("binary")) Some(poolEntryConfig.getString("binary")) else None
        PoolInfo.fromConfig(name, size, prefs, display, binaryLocation)
      }
    }
  }
}
