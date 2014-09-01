import sbt._
import Keys._

import com.typesafe.sbteclipse.plugin.EclipsePlugin
import EclipsePlugin.{ EclipseCreateSrc, EclipseKeys, EclipseProjectFlavor }
import net.virtualvoid.sbt.graph.{ Plugin => GraphPlugin }

trait Settings {
  private val default =
    Defaults.defaultSettings ++
    EclipsePlugin.settings ++
    GraphPlugin.graphSettings ++ Seq(
      organization := "com.ferega",
      version      := "0.3.0-SNAPSHOT",
      scalaVersion := "2.11.1",
      EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource,
      EclipseKeys.eclipseOutput := Some("bin")
    )

  val scala =
    default ++ Seq(
      EclipseKeys.projectFlavor := EclipseProjectFlavor.Scala,
      unmanagedSourceDirectories in Compile := (scalaSource in Compile).value :: Nil,
      unmanagedSourceDirectories in Test    := Nil,
      scalacOptions := Seq(
        "-deprecation",
        "-encoding", "UTF-8",
        "-feature",
        "-language:higherKinds",
        "-language:implicitConversions",
        "-language:postfixOps",
        "-optimise",
        "-unchecked",
        "-Xcheckinit",
        "-Xlint",
        "-Xno-uescape",
        "-Xverify",
        "-Yclosure-elim",
        "-Ydead-code",
        "-Yinline"
      )
    )

  val java =
    default ++ Seq(
      EclipseKeys.projectFlavor := EclipseProjectFlavor.Java,
      autoScalaLibrary          := false,
      unmanagedSourceDirectories in Compile := (javaSource in Compile).value :: Nil,
      unmanagedSourceDirectories in Test    := Nil,
      javacOptions := Seq(
        "-deprecation",
        "-encoding", "UTF-8",
        "-Xlint:all"
      )
    )

  val publishing = Seq(
    // credentials        += Credentials(Path.userHome / ".config" / "selenate" / "nexus.config"),
    crossScalaVersions := Seq("2.11.2", "2.10.4"),
    publishArtifact in (Compile, packageDoc) := false
    // publishTo := Some(
    //   if (version.value endsWith "SNAPSHOT") ElementSnapshots else ElementReleases
    // )
  )
}
