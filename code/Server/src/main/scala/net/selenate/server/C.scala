package net.selenate.server

import org.streum.configrity.Configuration

object C {
  private val configPath = sys.props("user.home") / ".config" / "selenate" / "server.config"
  private val config = Configuration.load(configPath)

  object Server {
    private val serverConfig = config.detach("server")
    val poolSize = serverConfig[Int]("pool-size")
  }
}