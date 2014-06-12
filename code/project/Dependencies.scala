import sbt._
import Keys._

trait Dependencies {
  val akkaVersion = "2.3.3"
  val akkaActor   = "com.typesafe.akka" %% "akka-actor"  % akkaVersion
  val akkaRemote  = "com.typesafe.akka" %% "akka-remote" % akkaVersion

  val jodaTimeConvert = "org.joda"  % "joda-convert" % "1.6"
  val jodaTimeTime    = "joda-time" % "joda-time"    % "2.3"

  val seleniumVersion = "2.42.1"
  val seleniumFirefox = "org.seleniumhq.selenium" % "selenium-firefox-driver" % seleniumVersion
  val seleniumServer  = "org.seleniumhq.selenium" % "selenium-server"         % seleniumVersion

  // ---------------------------------------------------------------------------

  val propsLoaderVersion = "0.0.0-SNAPSHOT"

  val dispatch         = "net.databinder.dispatch" %% "dispatch-core"        % "0.11.1"
  val logback          = "ch.qos.logback"          %  "logback-classic"      % "1.1.2"
  val propsLoaderScala = "com.ferega.props"        %% "propsloader-scalaapi" % propsLoaderVersion
  val propsLoaderJava  = "com.ferega.props"        %  "propsloader-javaapi"  % propsLoaderVersion
  val slf4j            = "org.slf4j"               %  "slf4j-api"            % "1.7.7"

  val akka     = Seq(akkaActor, akkaRemote)
  val jodaTime = Seq(jodaTimeConvert, jodaTimeTime)
  val selenium = Seq(seleniumFirefox, seleniumServer)
}
