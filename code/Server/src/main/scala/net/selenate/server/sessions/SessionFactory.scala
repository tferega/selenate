package net.selenate.server
package sessions

import actors.ActorFactory.{ typed, untyped }
import info.{ DisplayInfo, ProfileInfo }

import akka.actor.ActorRef
import net.selenate.common.sessions.{ ISessionFactory, SessionDisplay, SessionOptions }
import scala.collection.JavaConversions._
import scala.concurrent.Future

object SessionFactory extends ISessionFactory {
  val factory = typed[ISessionFactory]("session-factory", new SessionFactory)

  def getSession(sessionID: String, options: SessionOptions) = factory.getSession(sessionID, options)
  def getSession(sessionID: String) = factory.getSession(sessionID)
}

private class SessionFactory extends ISessionFactory {
  private def getProfile(options: SessionOptions) =
    new ProfileInfo(
        prefMap = options.getPreferences.getAll.toMap,
        display = options.getDisplay match {
          case main: SessionDisplay.Main           => DisplayInfo.Main
          case firstFree: SessionDisplay.FirstFree => DisplayInfo.FirstFree
          case specific: SessionDisplay.Specific   => DisplayInfo.Specific(specific.getNum)
        },
        binaryLocation  = Option(options.getBinaryLocation))

  private def getSessionDo(sessionID: String, optionsOpt: Option[SessionOptions]): Future[ActorRef] = Future {
    val profileOpt = optionsOpt map getProfile
    val profile    = profileOpt getOrElse ProfileInfo.default
    val name       = sessionID

    untyped(name, () => new SessionActor(sessionID, profile))
  }


  def getSession(sessionID: String, options: SessionOptions) =
    getSessionDo(sessionID, Some(options))

  def getSession(sessionID: String) =
    getSessionDo(sessionID, None)
}
