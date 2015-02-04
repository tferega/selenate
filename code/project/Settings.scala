import sbt._
import Keys._

import com.typesafe.sbteclipse.plugin.EclipsePlugin
import EclipsePlugin.{ EclipseCreateSrc, EclipseKeys, EclipseProjectFlavor }
import net.virtualvoid.sbt.graph.{ Plugin => GraphPlugin }

trait Settings {
  implicit def depToDepSeq(dep: ModuleID) = Seq(dep)
  
  def project(
      baseSettings: Seq[Def.Setting[_]],
      projectName: String,
      isPublish: Boolean = false)
      (deps: Seq[ModuleID]*) =
    Project(
      projectName,
      file(projectName),
      settings =
        baseSettings ++
        (if (isPublish) publishing else Seq.empty) ++
        Seq(
          name := "Selenate-" + projectName,
          libraryDependencies ++= deps.flatten
        )
    )

  private val default =
    Defaults.defaultSettings ++
    EclipsePlugin.settings ++
    GraphPlugin.graphSettings ++ Seq(
      organization := "com.ferega",
      version      := "0.3.0-SNAPSHOT",
      scalaVersion := "2.11.5",
      EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource,
      EclipseKeys.eclipseOutput := Some("bin")
    )

  val scalaSettings =
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

  val javaSettings =
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
    crossScalaVersions := Seq("2.11.5", "2.10.4"),
    publishArtifact in (Compile, packageDoc) := false
  )
}
