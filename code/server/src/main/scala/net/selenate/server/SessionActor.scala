package net.selenate.server

import akka.actor.Actor

class SessionActor(sessionID: String) extends Actor {
  def receive = {
    case "test" =>
      println("SESSION [%s] RECEIVED TEST" format sessionID)
    case _ =>
      println("SESSION [%s] RECEIVED UNKNOWN MESSAGE" format sessionID)
  }
}