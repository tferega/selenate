package net.selenate.server
package actions

import akka.actor.ActorRef

class SessionFactory extends ISessionFactory {
  def getSession(sessionID: String): String = {
    val name = "session-actor-"+ sessionID
    ActorFactory.untyped(name, () => new SessionActor(sessionID))
    name
  }
}