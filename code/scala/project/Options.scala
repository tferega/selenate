import sbt._
import Keys._



object Options {
  val Name            = "Selenate"
  val Organisation    = "net.selenate"
  val Version         = "0.0.1"
  val ScalaVersions   = Seq("2.9.2", "2.9.1-1", "2.9.1")
  val Repositories    = Seq(
    "Maven Central" at "http://repo1.maven.org/maven2/"
  )
}