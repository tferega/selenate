import sbt._
import Keys._

import Dependencies._
import Helpers._

object SelenateBuild extends Build {
  lazy val root = top(Seq(
    common,
    client,
    library,
    server
  ))

  lazy val client = project(
    "Client",
    ProjectFlavor.Java,
    Seq(akka),
    Seq(common)
  ) settings (
    Publishing.settings: _*
  )

  lazy val common = project(
    "Common",
    ProjectFlavor.Java
  ) settings (
    Publishing.settings: _*
  )

  lazy val library = project("Library", ProjectFlavor.Scala)

  lazy val server = project(
    "Server",
    ProjectFlavor.Scala,
    Seq(
      akka,
      selenium),
    Seq(common)
  ) settings (
    Publishing.settings: _*
  )
}
