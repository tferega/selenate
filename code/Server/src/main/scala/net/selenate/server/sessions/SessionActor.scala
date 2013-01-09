package net.selenate
package server
package sessions

import actions._
import common.comms.req._
import common.comms.res._
import akka.actor.{ Actor, ActorRef }
import org.openqa.selenium.firefox.{ FirefoxDriver, FirefoxProfile }
import org.openqa.selenium.OutputType
import scala.collection.JavaConversions._
import akka.util.duration._
import actors.ActorFactory

class SessionActor(sessionID: String, profile: FirefoxProfile) extends Actor {
  import context._

  private val d = new FirefoxDriver(profile)
  private var isKeepalive = false

  private def actionMan: PartialFunction[SeCommsReq, SeCommsRes] = {
    case arg: SeReqAppendText      => new AppendTextAction(d).act(arg)
    case arg: SeReqCapture         => new CaptureAction(d).act(arg)
    case arg: SeReqClearText       => new ClearTextAction(d).act(arg)
    case arg: SeReqClick           => new ClickAction(d).act(arg)
    case arg: SeReqClose           => new CloseAction(d).act(arg)
    case arg: SeReqElementExists   => new ElementExistsAction(d).act(arg)
    case arg: SeReqExecuteScript   => new ExecuteScriptAction(d).act(arg)
    case arg: SeReqFindAlert       => new FindAlertAction(d).act(arg)
    case arg: SeReqFindAndClick    => new FindAndClickAction(d).act(arg)
    case arg: SeReqFindElement     => new FindElementAction(d).act(arg)
    case arg: SeReqFindElementList => new FindElementListAction(d).act(arg)
    case arg: SeReqFindSelect      => new FindSelectAction(d).act(arg)
    case arg: SeReqGet             => new GetAction(d).act(arg)
    case arg: SeReqStartKeepalive  => new StartKeepaliveAction(d).act(arg)
    case arg: SeReqQuit            => new QuitAction(d).act(arg)
    case arg: SeReqResetFrame      => new ResetFrameAction(d).act(arg)
    case arg: SeReqSelectOption    => new SelectOptionAction(d).act(arg)
    case arg: SeReqSwitchFrame     => new SwitchFrameAction(d).act(arg)
    case arg: SeReqWaitFor         => new WaitForAction(d).act(arg)
  }

  private def receiveBase: Receive = {
    case "ping" => sender ! "pong"
    case msg @ KeepaliveMsg(delay, reqList) =>
      if (isKeepalive) {
        msg.reqList foreach actionMan
        schedulify(msg)
      }
    case arg: SeReqStartKeepalive =>
      sender ! actionMan(arg)
      isKeepalive = true
      val msg = KeepaliveMsg.fromReq(arg)
      schedulify(msg)
    case arg: SeCommsReq =>
      isKeepalive = false
      sender ! actionMan(arg)
  }

  private def wrap(base: Receive) = new Receive {
    def isDefinedAt(arg: Any) = base.isDefinedAt(arg)
    def apply(arg: Any) = {
      try {
        val clazz = arg.getClass.toString
        println("SESSION (%s) RECEIVED [%s] FROM %s".format(sessionID, clazz, sender.path.toString))
        base.apply(arg)
      } catch {
        case e: Exception =>
          if (sender == ActorFactory.system.deadLetters) {
            e.printStackTrace
          } else {
            println(e.toString)
          }

          sender ! new Exception(e.stackTrace)
      }
    }
  }

  def receive = wrap(receiveBase)

  private def schedulify(msg: KeepaliveMsg) {
    system.scheduler.scheduleOnce(msg.delay, self, msg)
  }
}
