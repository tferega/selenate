import sbt._
import Keys._

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
        (if (isPublish) publishing else nonPublishing) ++
        (mainClass match { case Some(mc) => getPackable(mc); case None => Seq.empty} ) ++
        Seq(
          name := "Selenate-" + projectName,
          libraryDependencies ++= deps.flatten
        )
    )

  private val default =
    Defaults.coreDefaultSettings ++ Seq(
      organization := "net.selenate",
      version      := "0.3.10",
      scalaVersion := "2.11.8"
    )

  val scalaSettings =
    default ++ Seq(
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
      autoScalaLibrary := false,
      crossPaths       := false,
      unmanagedSourceDirectories in Compile := (javaSource in Compile).value :: Nil,
      unmanagedSourceDirectories in Test    := Nil,
      javacOptions in (Compile, compile) := Seq(
        "-encoding", "UTF-8",
        "-Xlint:all"
      ),
      javacOptions in (Compile, doc) := Nil
    )

  val publishing = Seq(
    crossScalaVersions := Seq("2.11.8"),
    publishArtifact in (Compile, packageDoc) := true,
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

  val nonPublishing = Seq(
    packagedArtifacts := Map.empty
  )

  def getPackable(mainClass: String) =
    Pack.packSettings ++ Seq(
      Pack.packMain := Map("start" -> mainClass)
    )
}
