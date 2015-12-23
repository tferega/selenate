import sbt._
import Keys._

object SelenateBuild extends Build with Settings with Dependencies {
  lazy val client = project(javaSettings, "Client", isPublish = true)(
    akka
  ).dependsOn(common)
  
  lazy val common = project(javaSettings, "Common", isPublish = true)(
    akka, config
  )

  lazy val compiler = project(scalaSettings, "Compiler", isPublish = false)(
  )

  lazy val server = project(scalaSettings, "Server", isPublish = false, mainClass = Some("net.selenate.server.EntryPoint"))(
    akka, selenium, dispatch, logback, procrun, slf4j
  ).dependsOn(common)
  
  lazy val demo = project(javaSettings, "Demo", isPublish = false, mainClass = Some("net.selenate.demo.Demo"))(
    akka, logback
  ).dependsOn(common, client)
}
