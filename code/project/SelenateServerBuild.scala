import sbt.{ project => _, _ }
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
    Seq(akka, junit, s3ClientModel, s3ClientService % "test"),
    Seq(common)
  ) settings (
    (
       Seq(unmanagedSourceDirectories in Test := (javaSource in Test).value :: Nil) 
       ++ Publishing.settings
    ): _*
  )

  lazy val common = project(
    "Common",
    ProjectFlavor.Java,
    Seq(
      akka,
      scalaLibrary,
      s3ClientModel
    )
  ) settings (
    Publishing.settings: _*
  )

  lazy val library = project(
    "Library",
    ProjectFlavor.Scala,
    Seq(
      selenium,
      jodaTime,
      slf4j
    )
  ) settings (
    Publishing.settings: _*
  )

  lazy val server = project(
    "Server",
    ProjectFlavor.Scala,
    Seq(
      akka,
      configrity,
      dispatch,
      logback,
      selenium,
      slf4j,
      sikuli,
      tagSoup,
      s3ClientService),
    Seq(common)
  ) settings (
    (Seq(classpathTypes += "maven-plugin") ++ Publishing.settings): _*
  )
}
