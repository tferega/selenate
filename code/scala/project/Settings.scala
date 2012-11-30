import sbt._
import Keys._



// =============  PACKS THE REPOSITORIES INTO A SETTINGS VARIABLE  =============
object Resolvers {
  val settings = Seq(
    resolvers := Options.Repositories,
    externalResolvers <<= resolvers map { rs =>
      Resolver.withDefaultResolvers(rs, mavenCentral = false, scalaTools = false)
    }
  )
}



// ==============  PUBLISHING SETTINGS  ==============
object Publishing {
  val settings = Seq(
    publishTo <<= (version) { version => Some(
      if (version.endsWith("SNAPSHOT")) ("Element Snapshots" at "http://maven.element.hr/nexus/content/repositories/snapshots/") else ("Element Releases"  at "http://maven.element.hr/nexus/content/repositories/releases/")
    )},
    credentials += Credentials(Path.userHome / ".publish" / "element.credentials"),
    publishArtifact in (Compile, packageDoc) := false,
    crossScalaVersions := Seq("2.9.2", "2.9.1-1", "2.9.1")
  )
}



// ==============  DEFINES DEFAULT SETTINGS USED BY ALL PROJECTS  ==============
object Default {
  val settings =
    Defaults.defaultSettings ++
    Resolvers.settings ++
    Publishing.settings ++ Seq(
      organization       :=  Options.Organisation,
      crossScalaVersions :=  Options.ScalaVersions,
      scalaVersion       <<= (crossScalaVersions) { versions => versions.head },
      scalacOptions      :=  Seq("-unchecked", "-deprecation", "-encoding", "UTF-8", "-optimise"),
      unmanagedSourceDirectories in Compile <<= (scalaSource in Compile)(_ :: Nil),
      unmanagedSourceDirectories in Test    <<= (scalaSource in Test)(_ :: Nil)
  )
}



// ==================  HELPER FUNCTIONS FOR PROJECT CREATION  ==================
object Helpers {
  // Implicit magic that makes project definitions really simple to write.
  implicit def depToFunSeq(m: ModuleID) = Seq((_: String) => m)
  implicit def depFunToSeq(fm: String => ModuleID) = Seq(fm)
  implicit def depSeqToFun(mA: Seq[ModuleID]) = mA.map(m => ((_: String) => m))
  implicit def warName2SMA(name: String) = (_: String, _: ModuleID, _: Artifact) => name


  // Creates a main container project (one that contains all other project, and
  // is otherwise empty).
  def top(
      projectAggs: Seq[sbt.ProjectReference] = Seq()) =
    Project(
      Options.Name,
      file("."),
      settings = Default.settings ++ Seq(
        name    := Options.Name,
        version := Options.Version
      )
    ) aggregate(projectAggs: _*)


  // Creates a container project (one that contains sub-projects, and is
  // otherwise empty).
  def parent(
      title: String,
      projectAggs: Seq[sbt.ProjectReference] = Seq()) =
    Project(
      title,
      file(title.replace('-', '/')),
      settings = Default.settings ++ Seq(
        name    := Options.Name +"-"+ title,
        version := Options.Version
      )
    ) aggregate(projectAggs: _*)


  // Creates a proper project.
  def project(
      title: String,
      deps: Seq[Seq[String => ModuleID]] = Seq(),
      projectDeps: Seq[ClasspathDep[ProjectReference]] = Seq()) =
    Project(
      title,
      file(title.replace('-', '/')),
      settings = Default.settings ++ Seq(
        name    := Options.Name +"-"+ title,
        version := Options.Version
      ) :+ (libraryDependencies <++= scalaVersion( sV =>
        for (depSeq <- deps; dep <- depSeq) yield dep(sV))
      )
    ) dependsOn(projectDeps: _*)
}
