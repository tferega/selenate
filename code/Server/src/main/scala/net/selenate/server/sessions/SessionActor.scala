package net.selenate
package server
package sessions

import common.comms.req._
import common.comms.res._
import actions._
import actors.ActorFactory
import driver.DriverPool
import akka.actor.Actor
import org.openqa.selenium.firefox.FirefoxProfile
import net.selenate.common.comms.req.SeReqDownload

class SessionActor(sessionID: String, profile: FirefoxProfile) extends Actor {
  private val log  = Log(classOf[SessionActor], sessionID)

  import context._

  log.info("Creating session actor for session id: {%s}." format sessionID)
  private val d = DriverPool.get
  private var isKeepalive = false

  private def actionMan: PF[SeCommsReq, SeCommsRes] = {
    case arg: SeReqAppendText      => new AppendTextAction(d).act(arg)
    case arg: SeReqCapture         => new CaptureAction(d).act(arg)
    case arg: SeReqClearText       => new ClearTextAction(d).act(arg)
    case arg: SeReqClick           => new ClickAction(d).act(arg)
    case arg: SeReqClose           => new CloseAction(d).act(arg)
    case arg: SeReqDownload        => new DownloadAction(d).act(arg)
    case arg: SeReqElementExists   => new ElementExistsAction(d).act(arg)
    case arg: SeReqExecuteScript   => new ExecuteScriptAction(d).act(arg)
    case arg: SeReqFindAlert       => new FindAlertAction(d).act(arg)
    case arg: SeReqFindAndClick    => new FindAndClickAction(d).act(arg)
    case arg: SeReqFindElement     => new FindElementAction(d).act(arg)
    case arg: SeReqFindElementList => new FindElementListAction(d).act(arg)
    case arg: SeReqFindSelect      => new FindSelectAction(d).act(arg)
    case arg: SeReqGet             => new GetAction(d).act(arg)
    case arg: SeReqNavigateBack    => new NavigateBackAction(d).act(arg)
    case arg: SeReqNavigateForward => new NavigateForwardAction(d).act(arg)
    case arg: SeReqNavigateRefresh => new NavigateRefreshAction(d).act(arg)
    case arg: SeReqQuit            => new QuitAction(d).act(arg)
    case arg: SeReqResetFrame      => new ResetFrameAction(d).act(arg)
    case arg: SeReqSelectOption    => new SelectOptionAction(d).act(arg)
    case arg: SeReqStartKeepalive  => new StartKeepaliveAction(d).act(arg)
    case arg: SeReqStopKeepalive   => new StopKeepaliveAction(d).act(arg)
    case arg: SeReqSwitchFrame     => new SwitchFrameAction(d).act(arg)
    case arg: SeReqWaitFor         => new WaitForAction(d).act(arg)
  }

  private def receiveBase: Receive = {
    case "ping" => sender ! "pong"
    case data @ KeepaliveData(delay, reqList) =>
      if (isKeepalive) {
        log.info("Keepalive tick!")
        data.reqList foreach actionMan
        schedulify(data)
      }
    case arg: SeReqStartKeepalive =>
      sender ! actionMan(arg)
      if (!isKeepalive) {
        log.info("Entering keepalive mode.")
      }
      isKeepalive = true
      val data = KeepaliveData.fromReq(arg)
      schedulify(data)
    case arg: SeCommsReq =>
      if (isKeepalive) {
        log.info("Leaving keepalive mode.")
      }
      isKeepalive = false
      sender ! actionMan(arg)
  }

  private def wrap(base: Receive) = new Receive {
    def isDefinedAt(arg: Any) = base.isDefinedAt(arg)
    def apply(arg: Any) = {
      val clazz = arg.getClass.toString
      try {
        log.info("Received request [%s] from %s.".format(sessionID, clazz, sender.path.toString))
        log.debug(arg.toString)
        base.apply(arg)
      } catch {
        case e: Exception =>
          log.warn("An error occured while processing [%s]" format clazz)
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

  private def schedulify(data: KeepaliveData) {
    system.scheduler.scheduleOnce(data.delay, self, data)
  }
}
