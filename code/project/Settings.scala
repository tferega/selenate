import sbt._
import Keys._

import com.typesafe.sbteclipse.plugin.EclipsePlugin
import EclipsePlugin.{ EclipseCreateSrc, EclipseKeys, EclipseProjectFlavor }
import net.virtualvoid.sbt.graph.{ Plugin => GraphPlugin }
import xerial.sbt.Pack
trait Settings {
  import scala.language.implicitConversions
  implicit def depToDepSeq(dep: ModuleID) = Seq(dep)

  def project(
      baseSettings: Seq[Def.Setting[_]],
      projectName: String,
      isPublish: Boolean = false,
      mainClass: Option[String] = None)
      (deps: Seq[ModuleID]*) =
    Project(
      projectName,
      file(projectName),
      settings =
        baseSettings ++
        (if (isPublish) publishing else Seq.empty) ++
        (mainClass match { case Some(mc) => getPackable(mc); case None => Seq.empty} ) ++
        Seq(
          name := "Selenate-" + projectName,
          libraryDependencies ++= deps.flatten
        )
    )

  private val default =
    Defaults.coreDefaultSettings ++
    EclipsePlugin.settings ++
    GraphPlugin.graphSettings ++ Seq(
      organization := "net.selenate",
      version      := "0.3.5-SNAPSHOT",
      scalaVersion := "2.11.7",
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
      crossPaths                := false,
      unmanagedSourceDirectories in Compile := (javaSource in Compile).value :: Nil,
      unmanagedSourceDirectories in Test    := Nil,
      javacOptions := Seq(
        "-encoding", "UTF-8",
        "-Xlint:all"
      )
    )

  val publishing = Seq(
    crossScalaVersions := Seq("2.11.7", "2.10.4"),
    publishArtifact in (Compile, packageDoc) := false,
    publishArtifact in Test := false,
    publishTo := Some(
      if (version.value endsWith "SNAPSHOT") {
        Opts.resolver.sonatypeSnapshots
      } else {
        Opts.resolver.sonatypeStaging
      }
    ),
    publishMavenStyle       := true,
    pomIncludeRepository    := { _ => false },
    licenses                += ("MIT", url("http://opensource.org/licenses/MIT")),
    homepage                := Some(url("https://github.com/tferega/selenate")),
    credentials             += Credentials(Path.userHome / ".config" / "tferega.credentials"),
    startYear               := Some(2012),
    scmInfo                 := Some(ScmInfo(url("https://github.com/tferega/selenate"), "scm:git:https://github.com/tferega/selenate.git")),
    pomExtra                ~= (_ ++ {Developers.toXml})
  )

  def getPackable(mainClass: String) =
    Pack.packSettings ++ Seq(
      Pack.packMain := Map("start" -> mainClass)
    )
}
