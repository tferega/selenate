import sbt._
import Keys._

object SelenateBuild extends Build with Settings with Dependencies {
  lazy val client = Project(
    "Client",
    file("Client"),
    settings = java ++ publishing ++ Seq(
      name := "Selenate-Client",
      libraryDependencies := akka
    )
  ) dependsOn(common)

  lazy val common = Project(
    "Common",
    file("Common"),
    settings = java ++ publishing ++ Seq(
      name := "Selenate-Common",
      libraryDependencies := akka
    )
  )

  lazy val server = Project(
    "Server",
    file("Server"),
    settings = scala ++ publishing ++ Seq(
      name := "Selenate-Server",
      libraryDependencies := akka ++ selenium ++ Seq(dispatch, logback, slf4j)
    )
  ) dependsOn(common)
}
