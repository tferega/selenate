import sbt._
import Keys._

object Dependencies {
  val akkaVersion = "2.0.3"
  val akkaActor   = "com.typesafe.akka" % "akka-actor"  % akkaVersion
  val akkaRemote  = "com.typesafe.akka" % "akka-remote" % akkaVersion

  val seleniumVersion = "2.25.0"
  val seleniumFirefox = "org.seleniumhq.selenium" % "selenium-firefox-driver" % seleniumVersion
  val seleniumServer  = "org.seleniumhq.selenium" % "selenium-server"         % seleniumVersion

  val akka     = Seq(akkaActor, akkaRemote)
  val selenium = Seq(seleniumFirefox, seleniumServer)
}
