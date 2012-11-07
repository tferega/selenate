package net.selenate.server
package sessions

import actors.ActorFactory

class SessionFactory extends ISessionFactory {
  def getSession(sessionID: String): String = {
    val name = sessionID
    val session = ActorFactory.untyped(name, () => new SessionActor(sessionID))
    session.path.toString
  }
}