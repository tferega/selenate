package net.selenate.server
package driver

import java.io.File
import org.openqa.selenium.firefox.{ FirefoxBinary, FirefoxProfile }

object ProfileInfo {
  // ###############==========-----> DESERIALIZATION <-----==========###############
  private object Deserialization {
    def apply(s: String): ProfileInfo = {
      s.split("\\|", -1).toList match {
        case (screenPreference :: binaryLocation :: prefMap :: Nil) =>
          val pm = doPrefMap(prefMap)
          val sp = doScreenPreference(screenPreference)
          val bl = doBinaryLocation(binaryLocation)

          new ProfileInfo(
              prefMap          = pm,
              screenPreference = sp,
              binaryLocation   = bl)

        case _ => throw new Exception(s"Error while deserializing ProfileInfo. Offending entry: $s")
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
    def apply(profile: ProfileInfo): String = {
      val pm = doPrefMap(profile.prefMap)
      val sp = doScreenPreference(profile.screenPreference)
      val bl = doBinaryLocation(profile.binaryLocation)
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

  // ###############==========-----> GENERAL <-----==========###############
  def empty = new ProfileInfo(
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


class ProfileInfo(
    val prefMap: Map[String, AnyRef]       = Map.empty,
    val screenPreference: ScreenPreference = ScreenPreference.Default,
    val binaryLocation: Option[File]       = None) {
  import ProfileInfo._

  override def toString = s"ProfileInfo($signature)"

  val signature = Serialization(this)
}
