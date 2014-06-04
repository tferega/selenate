package net.selenate.server

object C {
  val branch             = sys.props("branch")
  private val configPath = sys.props("user.home") / ".config" / "selenate" / branch / "server.config"
  private val config     = ??? //Configuration.load(configPath)

  object Server {
    private val serverConfig = ??? //config.detach("server")
    val poolSize: Int          = ??? //serverConfig[Int]("pool-size")
    val host: String              = ??? ///serverConfig[String]("host")
    val defaultProfileOpt: Option[String] = ??? //serverConfig.get[String]("default-profile")
  }
}
