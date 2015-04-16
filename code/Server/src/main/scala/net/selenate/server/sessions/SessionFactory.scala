package net.selenate
package server
package sessions

import common.sessions.ISessionFactory
import actors.ActorFactory._
//import java.util.{ Map => JMap }
import scala.collection.JavaConverters._
//import scala.collection.JavaConversions._
import org.openqa.selenium.firefox.FirefoxProfile
import net.selenate.server.driver.DriverProfile
import net.selenate.common.user.Preferences
import scala.concurrent.Future
import akka.actor.ActorRef

object SessionFactory extends ISessionFactory {
  val factory = typed[ISessionFactory]("session-factory", new SessionFactory)

  def getSession(sessionID: String, preferences: Preferences) = factory.getSession(sessionID, preferences)
  def getSession(sessionID: String) = factory.getSession(sessionID)

  def getSession(sessionID: String, preferences: Preferences, useFrames: java.lang.Boolean) = factory.getSession(sessionID, preferences, useFrames)
  def getSession(sessionID: String, useFrames: java.lang.Boolean) = factory.getSession(sessionID, useFrames)
}

private class SessionFactory extends ISessionFactory {
  private def getProfile(prefMap: Map[String, AnyRef]) =
    new DriverProfile(prefMap)

  private val emptyProfile =
    DriverProfile.empty

  private def getSessionDo(sessionID: String, prefMapOpt: Option[Map[String, AnyRef]], useFrames: java.lang.Boolean): Future[ActorRef] = Future {
    val profileOpt = prefMapOpt map getProfile
    // default values!!!
    val defPreferences: Map[String, AnyRef] = scala.collection.immutable.Map(
      "browser.download.panel.shown"                 -> "false" // disable view of download panel
    , "browser.download.useDownloadDir"              -> "true"  // download automatically insted of asking where
//    , "browser.download.folderList"                  -> "2"     // setting to enable custom download folder
    , "browser.download.dir"                         -> "/tmp/ff-downloads/%s".format(sessionID) // custom download folder
    , "browser.download.manager.showAlertOnComplete" -> "false"
    , "browser.helperApps.neverAsk.saveToDisk"       -> "text/csv, application/octet-stream, application/pdf,application/x-download" // default action for mime types
    )

    val profile    = (profileOpt getOrElse emptyProfile).addPreferenceMap(defPreferences)

    val name       = sessionID

    untyped(name, () => new SessionActor(sessionID, profile, useFrames))
  }


  def getSession(sessionID: String, preferences: Preferences) =
    getSessionDo(sessionID, Some(preferences.getAll.asScala.toMap), true)

  def getSession(sessionID: String) =
    getSessionDo(sessionID, None, true)

  def getSession(sessionID: String, preferences: Preferences, useFrames: java.lang.Boolean) =
    getSessionDo(sessionID, Some(preferences.getAll.asScala.toMap), useFrames)

  def getSession(sessionID: String, useFrames: java.lang.Boolean) =
    getSessionDo(sessionID, None, useFrames)
}
