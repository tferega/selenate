package net.selenate.server

import akka.actor.Actor

class SessionActor(sessionID: String) extends Actor {
  def receive = {
    case "ping" =>
      println("SESSION [%s] RECEIVED TEST" format sessionID)
    case x =>
      println("SESSION [%s] RECEIVED UNKNOWN MESSAGE: %s".format(sessionID, x))
  }
}