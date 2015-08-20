package net.selenate.server
package sessions

import net.selenate.server.Log
import scala.concurrent.duration._
import net.selenate.server.actors.ActorFactory
import akka.actor.Props
import net.selenate.server.actors.HouseKeeper
import net.selenate.server.actors.CleanFirefoxLogs

/**
 * All housekeeping actions should be scheduled and called from here.
 * E.g. cleanup all logs, kill old unused firefox sessions(not yet available) etc..
 */
object HouseKeeping{
   private val log = Log(HouseKeeping.getClass)
   log.info("Called housekeeping")
   private val houseKeeper = ActorFactory.system.actorOf(Props[HouseKeeper])

   def schedule(interval: FiniteDuration){
     ActorFactory.system.scheduler.schedule(0 seconds, interval, houseKeeper, CleanFirefoxLogs)
   }

}