package net.selenate
package server
package sessions

import common.sessions.ISessionFactory
import actors.ActorFactory._
import java.util.{ Map => JMap }
import scala.collection.JavaConverters._
import scala.collection.JavaConversions._
import org.openqa.selenium.firefox.FirefoxProfile
import net.selenate.server.driver.DriverProfile
import net.selenate.common.user.Preferences

object SessionFactory extends ISessionFactory {
  val factory = typed[ISessionFactory]("session-factory", new SessionFactory)

  def getSession(sessionID: String, preferences: Preferences) = factory.getSession(sessionID, preferences)
  def getSession(sessionID: String) = factory.getSession(sessionID)
}

private class SessionFactory extends ISessionFactory {
  private def getProfile(prefMap: Map[String, AnyRef]) =
    new DriverProfile(prefMap)

  private val emptyProfile =
    DriverProfile.empty

  private def getSessionDo(sessionID: String, prefMapOpt: Option[Map[String, AnyRef]]): String = {
    val profileOpt = prefMapOpt map getProfile
    val profile    = profileOpt getOrElse emptyProfile
    val name       = sessionID

    val session = untyped(name, () => new SessionActor(sessionID, profile))
    session.path.toStringWithAddress(address)
  }


  def getSession(sessionID: String, preferences: Preferences) =
    getSessionDo(sessionID, Option(preferences.getAll.toMap))

  def getSession(sessionID: String): String =
    getSessionDo(sessionID, None)
}