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

  private def p(s: String, sender: ActorRef) {
    println("SESSION [%s] RECEIVED %s FROM %s".format(sessionID, s, sender.path.toString))
  }


  private def tryOrReport[T](sender: ActorRef) =
    tryOrElse[Exception, T] { e =>
      println(e.toString)
      sender ! e.stackTrace
      e
    }

  def receive = {
    case "ping" => tryOrReport(sender) {
      p("PING", sender)
    }
    case x: SeReqCapture => tryOrReport(sender) {
      import scala.collection.JavaConversions._
      p("CAPTURE", sender)
      sender ! capture(x)
    }
    case x: SeReqClick => tryOrReport(sender) {
      p("CLICK: "+ x.xpath, sender)
      d.findElementByXPath(x.xpath).click()
    }
    case x: SeReqClose => tryOrReport(sender) {
      p("CLOSE", sender)
      d.close()
    }
    case x: SeReqGet => tryOrReport(sender) {
      p("GET: "+ x.url, sender)
      d.get(x.url)
    }
    case x => tryOrReport(sender) {
      println("UNKNOWN MESSAGE: "+ x.toString)
      val m = x.toString
      if (m == "qwer") {
        d.get("https://verkkopankki.sampopankki.fi/html/index.html?site=SBNBFI&secsystem=E2")
      }

    }
  }
}