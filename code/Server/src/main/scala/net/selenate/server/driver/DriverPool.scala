package net.selenate
package server
package driver

import actors.ActorFactory

import java.util.UUID

import org.openqa.selenium.firefox.FirefoxDriver

import scala.collection.mutable.Queue
import scala.concurrent.{ Await, Future }
import scala.concurrent.duration._

object DriverPool {
  val size           = C.Server.poolSize
  val defaultProfile = C.Server.defaultProfileOpt map DriverProfile.fromString getOrElse DriverProfile.empty
  val defaultPool = ActorFactory.typed[IDriverPoolActor]("driver-pool", new DriverPoolActor(defaultProfile, size))

  def get(profile: DriverProfile) = {
    if (profile.signature == defaultProfile.signature) {
      defaultPool.get
    } else {
      new FirefoxDriver(profile.get)
    }
  }
}
