import sbt._
import Keys._

object Repositories {
  val SonatypeReleases  = "Sonatype Releases"  at "https://oss.sonatype.org/service/local/staging/deploy/maven2"
  val SonatypeSnapshots = "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
}
