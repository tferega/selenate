package net.selenate.server
package sessions

import actors.ActorFactory.{ typed, untyped }
import driver.ProfileInfo
import akka.actor.ActorRef
import net.selenate.common.sessions.ISessionFactory
import net.selenate.common.user.Options
import scala.collection.JavaConversions._
import scala.concurrent.Future
import net.selenate.server.driver.ScreenPreference

object SessionFactory extends ISessionFactory {
  val factory = typed[ISessionFactory]("session-factory", new SessionFactory)

  def getSession(sessionID: String, options: Options) = factory.getSession(sessionID, options)
  def getSession(sessionID: String) = factory.getSession(sessionID)
}

private class SessionFactory extends ISessionFactory {
  private def getProfile(options: Options) =
    new ProfileInfo(
        prefMap          = options.getPreferences.getAll.toMap,
        screenPreference = if (options.getUseScreen) ScreenPreference.FirstFree else ScreenPreference.Default,
        binaryLocation   = Option(options.getBinaryLocation))

  private val emptyProfile =
    ProfileInfo.empty

  private def getSessionDo(sessionID: String, optionsOpt: Option[Options]): Future[ActorRef] = Future {
    val profileOpt = optionsOpt map getProfile
    val profile    = profileOpt getOrElse emptyProfile
    val name       = sessionID

    untyped(name, () => new SessionActor(sessionID, profile))
  }


  def getSession(sessionID: String, options: Options) =
    getSessionDo(sessionID, Some(options))

  def getSession(sessionID: String) =
    getSessionDo(sessionID, None)
}
