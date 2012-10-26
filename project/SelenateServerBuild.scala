import sbt._
import Keys._

object SelenateServerBuild extends Build {
  import com.typesafe.sbteclipse.plugin.EclipsePlugin.{settings => eclipseSettings, _}

  lazy val defaults =
    Defaults.defaultSettings ++
    eclipseSettings ++ Seq(
      organization     := "net.selenate"
    , version          := "0.0.0-SNAPSHOT"
    
    , crossScalaVersions := Seq("2.9.2", "2.9.1-1", "2.9.1", "2.9.0-1", "2.9.0")
    , scalaVersion <<= crossScalaVersions(_.head)
    
    , scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "UTF-8", "-optimise")
    , javacOptions     := Seq("-deprecation", "-Xlint:unchecked", "-encoding", "UTF-8", "-source", "1.6", "-target", "1.6")

    , unmanagedSourceDirectories in Compile <<= (scalaSource in Compile, javaSource in Compile)(_ :: _ :: Nil)
    , unmanagedSourceDirectories in Test := Nil

    , autoScalaLibrary := true
//    , autoScalaLibrary := false
    , crossPaths       := false

    , resolvers := Seq(
        "Element Nexus"     at "http://maven.element.hr/nexus/content/groups/public/"
      , "Element Snapshots" at "http://maven.element.hr/nexus/content/repositories/releases/" 
      , "Element Releases"  at "http://maven.element.hr/nexus/content/repositories/snapshots/" 
      )
      
    , externalResolvers <<= resolvers map { r =>
        Resolver.withDefaultResolvers(r, mavenCentral = false)
      }
    , credentials += Credentials(Path.userHome / ".config" / "selenate-server" / "nexus.config")

    , EclipseKeys.projectFlavor :=  EclipseProjectFlavor.Java
    )

  lazy val common = Project(
    "SelenateServer-Common"
    file("common")
    settings = defaults ++ Seq(
    , libraryDependencies ++= Seq(
      )
    )
  )
}
