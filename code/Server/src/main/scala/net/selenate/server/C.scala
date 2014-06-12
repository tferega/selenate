package net.selenate.server

import com.ferega.props.sapi._

object C {
  private val props = new PropsLoader(true, "%user.home%" %/ ".config" %/ "selenate" %/ "%branch%" %/ "server.config")

  val branch = props.get[String]("branch")
  object Server {
    val poolSize          = props.get[Int]("server.pool-size")
    val host              = props.get[String]("server.host")
    val defaultProfileOpt = props.opt[String]("default-profile")
  }
}
