package net.selenate.server

import com.typesafe.config.ConfigFactory
import com.typesafe.config.{ Config, ConfigValueFactory }
import scala.collection.JavaConversions._
import settings.PoolSettings

object C extends CUtils {
  val CONFIG = ConfigFactory.empty()
      .withFallback(loadAppUser)
      .withFallback(loadAppReference)
      .withFallback(loadAkkaUser)
      .withFallback(loadAkkaReference)
  logTrace(s"""Effective config: $CONFIG""")

  val BRANCH = branch
  val OS_NAME = sys.props("os.name")

  object Server extends {
    val TRYO_TRACE  = CONFIG.getBoolean("server.tryo-trace")

    object Locations extends {
      val RECORDINGS = CONFIG.getString("server.locations.recordings")
    }

    object Timeouts extends {
      val PAGE_LOAD   = parseTimeout(CONFIG.getString("server.timeouts.page-load"))
      val SHUTDOWN    = parseTimeout(CONFIG.getString("server.timeouts.shutdown"))
      val VNC_STARTUP = parseTimeout(CONFIG.getString("server.timeouts.vnc-startup"))
    }

    object Pool extends {
      val SIZE           = CONFIG.getString("server.pool.size")
      val DISPLAY        = CONFIG.getString("server.pool.display")
      val DISPLAY_WIDTH  = CONFIG.getString("server.pool.display-width")
      val DISPLAY_HEIGHT = CONFIG.getString("server.pool.display-height")
      val VNC_HOST       = CONFIG.getString("server.pool.vnc-host")
      val BINARY         = if (CONFIG.hasPath("server.pool.binary")) Some(CONFIG.getString("server.pool.binary")) else None
      val PREFS          = CONFIG.getObject("server.pool.prefs").unwrapped.toMap.mapValues(_.toString)
    }
  }
}
