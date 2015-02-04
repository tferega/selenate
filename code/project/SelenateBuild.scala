import sbt._
import Keys._

object SelenateBuild extends Build with Settings with Dependencies {
  lazy val common = project(javaSettings, "Common", isPublish = true)(
    akka
  )

  lazy val server = project(scalaSettings, "Server", isPublish = true)(
    akka, selenium, config, dispatch, logback, procrun, slf4j
  ).dependsOn(common)
}
