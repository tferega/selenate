package net.selenate.server

import actors.ActorFactory
import sessions.{ ISessionFactory, SessionFactory }

import scala.annotation.tailrec

object EntryPoint extends App {
  try {
    println("SELENATE SERVER STARTING")
    println("PRESS ENTER TO SHUT DOWN")

    ActorFactory.typed[ISessionFactory]("session-factory", new SessionFactory)

    readLine
    println("SELENATE SERVER SHUTTING DOWN")
    ActorFactory.shutdown()
    Runtime.getRuntime.halt(0)
  } catch {
    case e: Exception =>
      println
      println("IT IS A CRASH")
      e.printStackTrace
      Runtime.getRuntime.halt(-1)
  }
}
