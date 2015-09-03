package net.selenate
package server
package driver

import actors.ActorFactory

import java.util.UUID


import org.openqa.selenium.remote.{DesiredCapabilities, RemoteWebDriver}

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
      new RemoteWebDriver(new java.net.URL("http://10.5.35.5:4444/wd/hub"), DesiredCapabilities.firefox()) //(profile.get)
    }
  }
}
