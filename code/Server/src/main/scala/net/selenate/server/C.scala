package net.selenate.server

import net.ceedubs.ficus.Ficus._
import scala.collection.JavaConversions._
import com.typesafe.config.ConfigValue
import net.selenate.server.info.PoolInfo
import com.typesafe.config.Config

object C {
  import CUtils._

  def main(args: Array[String]) {
    println(Server.Pool.poolInfoList)
    println(Server.Pool.defaultPool)
  }

  private val defaultConfig = CUtils.loadConfig("server.reference.config");
  private val userConfig    = loadConfig(configFile());
  private val config        = userConfig withFallback defaultConfig
println(config)
  object Server {
    private val serverConfig = config.getConfig("server")

    val host = serverConfig.as[String]("host")

    object Pool {
      private val poolConfig = serverConfig.getConfig("pool")
      private val defaultPoolEntryConfig = loadConfig("poolentry.reference.config")

      def poolInfoList: List[PoolInfo] = getKeySet(poolConfig)
          .toList
          .map(e => e -> poolConfig.getConfig(e))
          .map(getPoolInfo)
      def defaultPool = getPoolInfo(("default", defaultPoolEntryConfig))

      private def getPoolInfo(p: (String, Config)) = {
        val userPoolEntryConfig = p._2
        val poolEntryConfig     = userPoolEntryConfig withFallback defaultPoolEntryConfig
println(poolEntryConfig)
        val name           = p._1
        val size           = poolEntryConfig.getString("size")
        val prefs          = poolEntryConfig.getObject("prefs").unwrapped.toMap.mapValues(_.toString)
        val display        = poolEntryConfig.getString("display")
        val binaryLocation = if (poolEntryConfig.hasPath("binary")) Some(poolEntryConfig.getString("binary")) else None
        PoolInfo.fromConfig(name, size, prefs, display, binaryLocation)
      }
    }
  }
}
