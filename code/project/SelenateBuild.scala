import sbt._
import Keys._

object SelenateBuild extends Build with Settings with Dependencies {
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
      libraryDependencies := akka ++ selenium ++ Seq(config, dispatch, logback, procrun, slf4j)
    )
  ) dependsOn(common)
}
