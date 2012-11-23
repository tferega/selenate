import sbt._
import Keys._

object Repositories {
  val ElementNexus     = "Element Nexus"     at "http://repo.element.hr/nexus/content/groups/public/"
  val ElementSnapshots = "Element Snapshots" at "http://repo.element.hr/nexus/content/repositories/snapshots/"
  val ElementReleases  = "Element Releases"  at "http://repo.element.hr/nexus/content/repositories/releases/"
}

object SelenateServerBuild extends Build {
  import com.typesafe.sbteclipse.plugin.EclipsePlugin.{settings => eclipseSettings, _}
  import Repositories._

  lazy val defaults =
    Defaults.defaultSettings ++
    eclipseSettings ++ Seq(
      organization     := "net.selenate"
    , version          := "0.0.0-SNAPSHOT"

    , crossScalaVersions := Seq("2.9.2", "2.9.1-1", "2.9.1", "2.9.0-1", "2.9.0")
    , scalaVersion <<= crossScalaVersions(_.head)

    , scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "UTF-8", "-optimise")
    , javacOptions     := Seq("-deprecation", "-Xlint:unchecked", "-encoding", "UTF-8", "-source", "1.6", "-target", "1.6")

    , unmanagedSourceDirectories in Compile <<= (scalaSource in Compile)(_ :: Nil)
    , unmanagedSourceDirectories in Test := Nil

    , autoScalaLibrary := true
    , crossPaths       := false

    , resolvers := Seq(ElementNexus, ElementSnapshots, ElementReleases)

    , externalResolvers <<= resolvers map { r =>
        Resolver.withDefaultResolvers(r, mavenCentral = false)
      }
    , credentials += Credentials(Path.userHome / ".config" / "selenate-server" / "nexus.config")
    )

  lazy val publishing = Seq(
      publishTo <<= (version) { version => Some(
        if (version.endsWith("SNAPSHOT")) ElementSnapshots else ElementNexus
      )},
      credentials += Credentials(Path.userHome / ".config" / "selenate-server" / "nexus.config"),
      publishArtifact in (Compile, packageDoc) := false
  )

  lazy val common = Project(
    "SelenateServer-Common"
  , file("common")
  , settings = defaults ++ publishing ++ Seq(
      libraryDependencies ++= Seq(
      )
    , unmanagedSourceDirectories in Compile <<= (javaSource in Compile)(_ :: Nil)
    , EclipseKeys.projectFlavor :=  EclipseProjectFlavor.Java
    )
  )

  lazy val client = Project(
    "SelenateServer-Client"
  , file("client")
  , settings = defaults ++ Seq(
      libraryDependencies ++= Seq(
        "com.typesafe.akka" % "akka-actor" % "2.0.3"
      , "com.typesafe.akka" % "akka-remote" % "2.0.3"
      )
    , unmanagedSourceDirectories in Compile <<= (javaSource in Compile)(_ :: Nil)
    , EclipseKeys.projectFlavor :=  EclipseProjectFlavor.Java
    )
  ) dependsOn(common)

  lazy val server = Project(
    "SelenateServer"
  , file("server")
  , settings = defaults ++ Seq(
      libraryDependencies ++= Seq(
        "com.typesafe.akka" % "akka-actor" % "2.0.3"
      , "com.typesafe.akka" % "akka-remote" % "2.0.3"
      , "org.seleniumhq.selenium" % "selenium-firefox-driver" % "2.25.0"
      , "org.seleniumhq.selenium" % "selenium-server"         % "2.25.0"
      )    , unmanagedSourceDirectories in Compile <<= (scalaSource in Compile)(_ :: Nil)
    )
  ) dependsOn(common)
}
