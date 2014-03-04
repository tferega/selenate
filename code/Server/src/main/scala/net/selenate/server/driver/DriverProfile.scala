package net.selenate
package server
package driver

import org.openqa.selenium.firefox.FirefoxProfile

object DriverProfile {
  // ###############==========-----> DESERIALIZATION <-----==========###############
  private object Deserialization {
    def apply(s: String): DriverProfile = {
      s.split("\\|").toList match {
        case (screenPreference :: entries :: Nil) =>
          val sp = doScreenPreference(screenPreference)
          val pm = doEntries(entries)
          new DriverProfile(sp, pm)
        case (screenPreference :: Nil) =>
          val sp = doScreenPreference(screenPreference)
          new DriverProfile(sp)
        case _ => throw new Exception(s"Error while deserializing DriverProfile. Offending entry: $s")
      }
    }

    private def doScreenPreference(sp: String) =
      ScreenPreference.fromString(sp)

    private def doEntries(entries: String) = {
      val entryList = entries.split(";")
      entryList.flatMap(doEntry).toMap
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
  }

  // ###############==========-----> SERIALIZATION <-----==========###############
  private object Serialization {
    def apply(p: DriverProfile): String = {
      val sp = doScreenPreference(p.screenPreference)
      val pm = doPrefMap(p.prefMap)
      s"$sp|$pm"
    }

    private def doScreenPreference(sp: ScreenPreference) =
      sp.toString

    def key(e: (String, AnyRef)) = e._1
    private def doPrefMap(pm: Map[String, AnyRef]) =
      pm
          .toSeq
          .sortBy(key)
          .map(serializeEntry)
          .mkString(";")

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

  def empty = new DriverProfile(ScreenPreference.Default, Map.empty)
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
    val screenPreference: ScreenPreference,
    val prefMap: Map[String, AnyRef]) {
  import DriverProfile._

  def this() =
    this(ScreenPreference.Default, Map.empty[String, AnyRef])
  def this(prefMap: Map[String, AnyRef]) =
    this(ScreenPreference.Default, prefMap)
  def this(screenPreference: ScreenPreference) =
    this(screenPreference, Map.empty[String, AnyRef])

  override def toString = "DriverProfile(%s)" format signature

  /*private[driver]*/ val get = FFP.get(prefMap)
  /*private[driver]*/ val signature = Serialization(this)
}
