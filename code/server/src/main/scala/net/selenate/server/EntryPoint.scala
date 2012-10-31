package net.selenate.server

import actions._
import comms._

import scala.annotation.tailrec

import akka.actor._
import akka.event.Logging
import com.typesafe.config.ConfigFactory
import akka.routing._

object EntryPoint extends App {
  try {
    val sessionFactoryActor = ActorFactory.typed("session-factory", new SessionFactory)

/*
      val ucRouter = system.actorOf(
        Props[Actor].withRouter(
          RoundRobinRouter(routees = ucs)
        )
      , "ss0"
      )
*/
    val bR = new java.io.BufferedReader(new java.io.InputStreamReader(System.in))
    @tailrec
    def waitForExit() {
      print("Enter thy command: ")
      val line = bR.readLine()
      if (line != "x") {
        println("""Unknown command "%s"""" format line)
        waitForExit()
      }
    }

    waitForExit()
    println("Shutting down...")
    println("Exiting!")
    ActorFactory.shutdown()
    Runtime.getRuntime.halt(0)

  }
  catch {
    case e =>
      println("IT IS A CRASH: ")
      e.printStackTrace()
      Runtime.getRuntime.halt(-1)
  }
}
