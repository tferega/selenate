package net.selenate
package server
package driver

import java.io.File

import org.openqa.selenium.firefox.{ FirefoxBinary, FirefoxProfile }

object DriverProfile {
  // ###############==========-----> DESERIALIZATION <-----==========###############
  private object Deserialization {
    def apply(s: String): DriverProfile = {
      s.split("\\|", -1).toList match {
        case (screenPreference :: binaryLocation :: prefMap :: Nil) =>
          val pm = doPrefMap(prefMap)
          val sp = doScreenPreference(screenPreference)
          val bl = doBinaryLocation(binaryLocation)

          new DriverProfile(
              prefMap          = pm,
              screenPreference = sp,
              binaryLocation   = bl)

        case _ => throw new Exception(s"Error while deserializing DriverProfile. Offending entry: $s")
      }
    }

    private def doPrefMap(prefMap: String) = {
      val entryList = prefMap.split(";")
      entryList.flatMap(doEntry).toMap
    }

   private def doScreenPreference(sp: String) =
      ScreenPreference.fromString(sp)

   private def doBinaryLocation(bl: String) =
     bl match {
       case "" => None
       case IsFile(file) => Some(file)
       case _ => throw new Exception(s"Error while deserializing File. Offending entry: $bl")
     }

    private def doEntry(entry: String): Option[(String, AnyRef)] =
      entry.split("=").toList match {
        case key :: IsBoolean(value) :: Nil => Some(key -> value)
        case key :: IsInt(value)     :: Nil => Some(key -> value)
        case key :: IsString(value)  :: Nil => Some(key -> value)
        case "" :: Nil => None
        case _ => throw new Exception(s"Error while deserializing profile entry. Offending entry: $entry")
      }

    private object IsString {
      private val R = """'(.*)'"""r
      def unapply(raw: String): Option[java.lang.String] =
        tryo {
          val R(extracted) = raw
          extracted
        }
    }

    private object IsInt {
      def unapply(raw: String): Option[java.lang.Integer] = tryo(raw.toInt)
    }

    private object IsBoolean {
      def unapply(raw: String): Option[java.lang.Boolean] = tryo(raw.toBoolean)
    }

    private object IsFile {
      def unapply(raw: String): Option[File] = tryo(new File(raw))
    }
  }

  // ###############==========-----> SERIALIZATION <-----==========###############
  private object Serialization {
    def apply(p: DriverProfile): String = {
      val pm = doPrefMap(p.prefMap)
      val sp = doScreenPreference(p.screenPreference)
      val bl = doBinaryLocation(p.binaryLocation)
      s"$pm|$sp|$bl"
    }

    def key(e: (String, AnyRef)) = e._1
    private def doPrefMap(pm: Map[String, AnyRef]) =
      pm
          .toSeq
          .sortBy(key)
          .map(serializeEntry)
          .mkString(";")

    private def doScreenPreference(sp: ScreenPreference) =
      sp.toString

    private def doBinaryLocation(bl: Option[File]) =
      bl.map(_.toString) getOrElse ""


    private def serializeEntry(entry: (String, AnyRef)) =
      s"${ entry._1 }='${ entry._2 }'"
  }

  // ###############==========-----> FireFox Profile <-----==========###############
  private object FFP {
    def get(prefMap: Map[String, AnyRef]) = {
      val ffp = new FirefoxProfile
      prefMap foreach addPref(ffp)
      ffp
    }

    private def addPref(ffp: FirefoxProfile)(pref: (String, AnyRef)) {
      val k = pref._1
      val v = pref._2

      v match {
        case x: java.lang.Boolean => ffp.setPreference(k, x)
        case x: java.lang.Integer => ffp.setPreference(k, x)
        case x: java.lang.String  => ffp.setPreference(k, x)
        case x => throw new IllegalArgumentException("Unsupported type: %s" format x.getClass.getCanonicalName)
      }
    }
  }

  // ###############==========-----> FireFox Binary <-----==========###############
  private object FFB {
    def get(binaryLocation: Option[File]) =
      binaryLocation match {
        case Some(bl) => new FirefoxBinary(bl)
        case None     => new FirefoxBinary()
      }
  }

  def empty = new DriverProfile(
      prefMap          = Map.empty,
      screenPreference = ScreenPreference.Default,
      binaryLocation   = None)
  def fromString(s: String) = Deserialization(s)
}


sealed trait ScreenPreference
object ScreenPreference {
  def fromString(s: String): ScreenPreference = {
    s match {
      case "Default"   => Default
      case "FirstFree" => FirstFree
      case _ => throw new Exception(s"Error while deserializing ScreenPreference. Offending entry: $s")
    }
  }
  case object Default   extends ScreenPreference
  case object FirstFree extends ScreenPreference
}


class DriverProfile(
    val prefMap: Map[String, AnyRef]       = Map.empty,
    val screenPreference: ScreenPreference = ScreenPreference.Default,
    val binaryLocation: Option[File]       = None) {
  import DriverProfile._

  override def toString = "DriverProfile(%s)" format signature

  val signature = Serialization(this)

  val ffProfile = FFP.get(prefMap)
  val ffBinary = FFB.get(binaryLocation)

  def runFirefox() = FirefoxRunner.run(this)
}
