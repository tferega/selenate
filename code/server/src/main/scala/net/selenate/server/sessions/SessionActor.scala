package net.selenate.server
package sessions

import actions._
import comms.req._
import comms.res._
import akka.actor.{ Actor, ActorRef }
import org.openqa.selenium.firefox.{ FirefoxDriver, FirefoxProfile }
import org.openqa.selenium.OutputType
import scala.collection.JavaConversions

class SessionActor(sessionID: String, profile: FirefoxProfile) extends Actor {
  private val d = new FirefoxDriver(profile)

  private def capture = new CaptureAction(d).act

  def receiveBase(sender: ActorRef): PartialFunction[Any, Unit] = {
    case "ping" =>
    case arg: SeReqCapture =>
      sender ! capture(arg)
    case arg: SeReqClick =>
      d.findElementByXPath(arg.xpath).click()
    case arg: SeReqClose =>
      d.close()
    case arg: SeReqGet =>
      d.get(arg.url)
    case arg =>
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