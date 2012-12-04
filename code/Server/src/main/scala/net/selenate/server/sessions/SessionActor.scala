package net.selenate
package server
package sessions

import actions._
import common.comms.req._
import common.comms.res._
import akka.actor.{ Actor, ActorRef }
import org.openqa.selenium.firefox.{ FirefoxDriver, FirefoxProfile }
import org.openqa.selenium.OutputType
import scala.collection.JavaConversions

class SessionActor(sessionID: String, profile: FirefoxProfile) extends Actor {
  private val d = new FirefoxDriver(profile)

  private def capture       = new CaptureAction(d).act
  private def click         = new ClickAction(d).act
  private def close         = new CloseAction(d).act
  private def element       = new ElementAction(d).act
  private def executeScript = new ExecuteScriptAction(d).act
  private def findAlert     = new FindAlertAction(d).act
  private def get           = new GetAction(d).act
  private def quit          = new QuitAction(d).act

  def receiveBase(sender: ActorRef): PartialFunction[Any, Unit] = {
    case "ping"                  => sender ! "pong"
    case arg: SeReqCapture       => sender ! capture(arg)
    case arg: SeReqClick         => sender ! click(arg)
    case arg: SeReqClose         => sender ! close(arg)
    case arg: SeReqElement       => sender ! element(arg)
    case arg: SeReqExecuteScript => sender ! executeScript(arg)
    case arg: SeReqFindAlert     => sender ! findAlert(arg)
    case arg: SeReqQuit          => sender ! quit(arg)
    case arg: SeReqGet           => sender ! get(arg)
  }

  def receive = new PartialFunction[Any, Unit] {
    def isDefinedAt(arg: Any) = receiveBase(sender).isDefinedAt(arg)
    def apply(arg: Any) = {
      try {
        val clazz = arg.getClass.toString
        println("SESSION (%s) RECEIVED [%s] FROM %s".format(sessionID, clazz, sender.path.toString))
        receiveBase(sender).apply(arg)
      } catch {
        case e: Exception =>
          println(e.toString)
          sender ! e.stackTrace
      }
    }
  }
}