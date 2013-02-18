package net.selenate
package server
package driver

import actors.ActorFactory

import akka.dispatch.{ Await, Future }
import akka.util.duration._

import java.util.UUID

import org.openqa.selenium.firefox.FirefoxDriver

import scala.collection.mutable.Queue

object DriverPool {
  val size           = C.Server.poolSize
  val defaultProfile = DriverProfile.fromString(C.Server.defaultProfileOpt.getOrElse(""))
  println(defaultProfile)

  val defaultPool = ActorFactory.typed[IDriverPoolActor]("driver-pool", new DriverPoolActor(defaultProfile, size))

  def get(profile: DriverProfile) = {
    if (profile.signature == defaultProfile.signature) {
      defaultPool.get
    } else {
      new FirefoxDriver(profile.get)
    }
  }
}
