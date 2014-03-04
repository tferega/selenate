package net.selenate.server

import org.streum.configrity.Configuration

case class ProfileSettings(name: String, size: Int, settings: Option[String])

object C {
  private val configPath = sys.props("user.home") / ".config" / "selenate" / "server.config"
  private val config = Configuration.load(configPath)

  object Server {
    private val serverConfig = config.detach("server")

    object Pool {
      private val poolConfig = serverConfig.detach("pool")
      private def getProfileSettings(p: (String, Configuration)) = {
        val name = p._1
        val config = p._2

        val size = config[Int]("size")
        val settings = config.get[String]("settings")
        ProfileSettings(name, size, settings)
      }

      val profileSettingsList = poolConfig.detachAll map getProfileSettings
    }
  }
}
