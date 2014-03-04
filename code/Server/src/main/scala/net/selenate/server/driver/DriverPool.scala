package net.selenate
package server
package driver

import actors.ActorFactory
import java.util.UUID
import org.openqa.selenium.firefox.FirefoxDriver
import scala.collection.mutable.Queue
import scala.concurrent.{ Await, Future }
import scala.concurrent.duration._
import org.openqa.selenium.firefox.FirefoxBinary

object DriverPool {
  private def createPool(ps: ProfileSettings) = {
    val profile = ps.settings
        .map(DriverProfile.fromString)
        .getOrElse(DriverProfile.empty)
    val actorName = s"driver-pool_${ ps.name }"
    ActorFactory.typed[IDriverPoolActor](actorName, new DriverPoolActor(profile, ps.size))
  }

  private val poolList = C.Server.Pool.profileSettingsList map createPool

  def get(profile: DriverProfile) = {
    poolList.find(_.signature == profile.signature) match {
      case Some(pool) =>
        pool.get
      case None =>
        profile.runFirefox()
    }
  }
}
