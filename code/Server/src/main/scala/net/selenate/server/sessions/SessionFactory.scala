package net.selenate
package server
package sessions

import common.sessions.ISessionFactory

import actors.ActorFactory._

import java.util.{ Map => JMap }

import scala.collection.JavaConverters._
import scala.collection.JavaConversions._

import org.openqa.selenium.firefox.FirefoxProfile

object SessionFactory extends ISessionFactory {
  val factory = typed[ISessionFactory]("session-factory", new SessionFactory)

  def getSession(sessionID: String, profileMapRaw: JMap[String, String]) = factory.getSession(sessionID, profileMapRaw)
  def getSession(sessionID: String) = factory.getSession(sessionID)
}

private class SessionFactory extends ISessionFactory {
  private def updateProfile(p: FirefoxProfile)(kv: (String, String)) {
    p.setPreference(kv._1, kv._2)
  }

  private def getProfile(profileMap: Map[String, String]): FirefoxProfile = {
    val p = new FirefoxProfile
    profileMap foreach updateProfile(p)
    p
  }

  private val emptyProfile =
    new FirefoxProfile

  private def getSessionDo(sessionID: String, profileMapOpt: Option[Map[String, String]]): String = {
    val profileOpt = profileMapOpt map getProfile
    val profile    = profileOpt getOrElse emptyProfile
    val name       = sessionID

    val session = untyped(name, () => new SessionActor(sessionID, profile))
    session.path.toStringWithAddress(address)
  }


  def getSession(sessionID: String, profileMapRaw: JMap[String, String]) =
    getSessionDo(sessionID, Option(profileMapRaw.toMap))

  def getSession(sessionID: String): String =
    getSessionDo(sessionID, None)
}