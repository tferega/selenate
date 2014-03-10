package net.selenate.server
package sessions

import actors.ActorFactory.{ typed, untyped }
import driver.ProfileInfo

import akka.actor.ActorRef
import net.selenate.common.sessions.ISessionFactory
import net.selenate.common.user.Preferences
import scala.collection.JavaConversions._
import scala.concurrent.Future

object SessionFactory extends ISessionFactory {
  val factory = typed[ISessionFactory]("session-factory", new SessionFactory)

  def getSession(sessionID: String, preferences: Preferences) = factory.getSession(sessionID, preferences)
  def getSession(sessionID: String) = factory.getSession(sessionID)

  def getSession(sessionID: String, preferences: Preferences, useFrames: java.lang.Boolean) = factory.getSession(sessionID, preferences, useFrames)
  def getSession(sessionID: String, useFrames: java.lang.Boolean) = factory.getSession(sessionID, useFrames)
}

private class SessionFactory extends ISessionFactory {
  private def getProfile(prefMap: Map[String, AnyRef]) =
    new ProfileInfo(
        prefMap = prefMap)

  private val emptyProfile =
    ProfileInfo.empty

  private def getSessionDo(sessionID: String, prefMapOpt: Option[Map[String, AnyRef]], useFrames: java.lang.Boolean): Future[ActorRef] = Future {
    val profileOpt = prefMapOpt map getProfile
    val profile    = profileOpt getOrElse emptyProfile
    val name       = sessionID

    untyped(name, () => new SessionActor(sessionID, profile, useFrames))
  }


  def getSession(sessionID: String, preferences: Preferences) =
    getSessionDo(sessionID, Some(preferences.getAll.toMap), true)

  def getSession(sessionID: String) =
    getSessionDo(sessionID, None, true)

  def getSession(sessionID: String, preferences: Preferences, useFrames: java.lang.Boolean) =
    getSessionDo(sessionID, Some(preferences.getAll.toMap), useFrames)

  def getSession(sessionID: String, useFrames: java.lang.Boolean) =
    getSessionDo(sessionID, None, useFrames)
}
