package net.selenate.server
package sessions

import actions._
import extensions.SelenateFirefox

import akka.actor.{ Actor, Cancellable, Props }
import net.selenate.common.comms.req._
import net.selenate.common.comms.res.SeCommsRes
import scala.concurrent.duration.Duration

object SessionActor {
  def props(sessionID: String, d: SelenateFirefox, useFrames: Boolean) = Props(new SessionActor(sessionID, d, useFrames))
}

class SessionActor(sessionID: String, d: SelenateFirefox, useFrames: Boolean = true)
    extends Actor
    with Loggable {
  logInfo(s"""Session actor for session "$sessionID" started""" format sessionID)
  private var keepaliveScheduler: Option[Cancellable] = None
  private def isKeepalive = keepaliveScheduler.isDefined
  implicit val actionContext = ActionContext(useFrames)

  private def actionMan: PF[SeCommsReq, SeCommsRes] = {
    case arg: SeReqAddCookie          => new AddCookieAction(d).act(arg)
    case arg: SeReqAppendText         => new AppendTextAction(d).act(arg)
    case arg: SeReqCapture            => new CaptureAction(d).act(arg)
    case arg: SeReqCaptureElement     => new CaptureElementAction(d).act(arg)
    case arg: SeReqCaptureWindow      => new CaptureWindowAction(d).act(arg)
    case arg: SeReqClearText          => new ClearTextAction(d).act(arg)
    case arg: SeReqClick              => new ClickAction(d).act(arg)
    case arg: SeReqClose              => new CloseAction(d).act(arg)
    case arg: SeReqDeleteCookieNamed  => new DeleteCookieNamedAction(d).act(arg)
    case arg: SeReqDownload           => new DownloadAction(d).act(arg)
    case arg: SeReqElementExists      => new ElementExistsAction(d).act(arg)
    case arg: SeReqExecuteScript      => new ExecuteScriptAction(d).act(arg)
    case arg: SeReqFindAlert          => new FindAlertAction(d).act(arg)
    case arg: SeReqFindAndClick       => new FindAndClickAction(d).act(arg)
    case arg: SeReqFindElement        => new FindElementAction(d).act(arg)
    case arg: SeReqFindElementList    => new FindElementListAction(d).act(arg)
    case arg: SeReqFindSelect         => new FindSelectAction(d).act(arg)
    case arg: SeReqGet                => new GetAction(d).act(arg)
    case arg: SeReqNavigateBack       => new NavigateBackAction(d).act(arg)
    case arg: SeReqNavigateForward    => new NavigateForwardAction(d).act(arg)
    case arg: SeReqNavigateRefresh    => new NavigateRefreshAction(d).act(arg)
    case arg: SeReqQuit               => new QuitAction(d).act(arg)
    case arg: SeReqResetFrame         => new ResetFrameAction(d).act(arg)
    case arg: SeReqSelectOption       => new SelectOptionAction(d).act(arg)
    case arg: SeReqSetUseFrames       => new SetUseFramesAction(d).act(arg)
    case arg: SeReqStartKeepalive     => new StartKeepaliveAction(d).act(arg)
    case arg: SeReqStopKeepalive      => new StopKeepaliveAction(d).act(arg)
    case arg: SeReqSwitchFrame        => new SwitchFrameAction(d).act(arg)
    case arg: SeReqSystemClick        => new SystemClickAction(d).act(arg)
    case arg: SeReqSystemInput        => new SystemInputAction(d).act(arg)
    case arg: SeReqWaitFor            => new WaitForAction(d).act(arg)
    case arg: SeReqWaitForBrowserPage => new WaitForBrowserPageAction(d).act(arg)
  }

  private def receiveBase: Receive = {
    case "ping" => sender ! "pong"
    case data @ KeepaliveData(delay, reqList) =>
      logInfo("Keepalive tick!")
      data.reqList foreach actionMan
    case arg: SeReqStartKeepalive =>
      sender ! actionMan(arg)
      startKeepalive(KeepaliveData.fromReq(arg))
    case arg: SeReqSetUseFrames =>
      actionContext.useFrames = arg.useFrames
      sender ! actionMan(arg)
    case arg: SeCommsReq =>
      stopKeepalive
      sender ! actionMan(arg)
  }

  private def wrap(base: Receive) = new Receive {
    def isDefinedAt(arg: Any) = base.isDefinedAt(arg)
    def apply(arg: Any) = {
      val clazz = arg.getClass.toString
      try {
        logInfo("Received request [%s] from %s.".format(sessionID, clazz, sender.path.toString))
        logDebug(arg.toString)
        base.apply(arg)
      } catch {
        case e: Exception =>
          logWarn("An error occured while processing [%s]" format clazz)
          if (sender == actors.system.deadLetters) {
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
    context.system.scheduler.scheduleOnce(data.delay, self, data)
  }

  private def startKeepalive(data: KeepaliveData) {
    stopKeepalive
    logInfo("Starting keepalive.")
    logDebug(keepaliveStatus)
    keepaliveScheduler = Some(context.system.scheduler.schedule(Duration.Zero, data.delay, self, data))
  }

  private def stopKeepalive() {
    logInfo("Stopping keepalive.")
    logDebug(keepaliveStatus)
    keepaliveScheduler.map(_.cancel)
    keepaliveScheduler = None
  }

  private def keepaliveStatus =
    "Previous status: %s.".format(if (isKeepalive) "running" else "stopped")
}
