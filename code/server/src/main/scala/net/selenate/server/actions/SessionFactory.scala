package net.selenate.server
package actions

import akka.actor.ActorRef

class SessionFactory extends ISessionFactory {
  def getSession(sessionID: String): String = {
    println("GOT A REQUEST FOR SESSION: "+ sessionID)
    SessionCache.get(sessionID) match {
      case None =>
        println("SESSION NOT FOUND IN CACHE; CREATING")
        val name = sessionID
        val session = ActorFactory.untyped(name, () => new SessionActor(sessionID))
        SessionCache(sessionID) = session
        session.path.name
      case Some(session) =>
        println("SESSION FOUND IN CACHE; REUSING")
        session.path.name
    }
  }
}