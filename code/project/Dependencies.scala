import sbt._
import Keys._

trait Dependencies {
  val akkaVersion = "2.4.4"
  val akkaActor   = "com.typesafe.akka" %% "akka-actor"  % akkaVersion
  val akkaRemote  = "com.typesafe.akka" %% "akka-remote" % akkaVersion
  val akkaSlf4j   = "com.typesafe.akka" %% "akka-slf4j"  % akkaVersion

  val jodaTimeConvert = "org.joda"  % "joda-convert" % "1.8.1"
  val jodaTimeTime    = "joda-time" % "joda-time"    % "2.9.3"

  val seleniumVersion = "2.53.0"
  val seleniumFirefox = "org.seleniumhq.selenium" % "selenium-firefox-driver" % seleniumVersion
  val seleniumServer  = "org.seleniumhq.selenium" % "selenium-server"         % seleniumVersion

  // ---------------------------------------------------------------------------

  val config           = "com.typesafe"            %  "config"               % "1.3.0"
  val dispatch         = "net.databinder.dispatch" %% "dispatch-core"        % "0.11.3"
  val logback          = "ch.qos.logback"          %  "logback-classic"      % "1.1.7"
  val slf4j            = "org.slf4j"               %  "slf4j-api"            % "1.7.21"
  val procrun          = "com.ferega.procrun"      %% "processrunner"        % "0.1.5"

  val akka     = Seq(akkaActor, akkaRemote, akkaSlf4j)
  val jodaTime = Seq(jodaTimeConvert, jodaTimeTime)
  val selenium = Seq(seleniumFirefox, seleniumServer)
}
