package net.selenate.server
package sessions

import driver.DriverPoolActor

import akka.actor.{ Actor, Props }
import net.selenate.common.sessions.SessionRequest

object SessionFactoryActor {
  def props() = Props(new SessionFactoryActor())
}

class SessionFactoryActor extends Actor {
  import SessionFactoryActor._

  private val log = Log(this.getClass)

  def receive = {
    case req: SessionRequest =>
      log.debug(s"""Received SessionRequest(${ req.getSessionID })""")
      context.actorSelection("akka://server-system/user/driver-pool") ! DriverPoolActor.Dequeue(req.getSessionID)

    case (sessionID: String, DriverPoolActor.DriverEntry(uuid, driverFuture)) =>
      log.debug(s"""Received DriverEntry($uuid) for session "$sessionID"""")
      driverFuture.onSuccess {
        case driver =>
          log.debug(s"""Driver future for session "$sessionID" completed""")
          context.actorOf(SessionActor.props(sessionID, driver, false), sessionID)
      }
  }
}
