package net.selenate.server
package sessions

import actions.ActionContext
import actions.workers._
import extensions.SelenateFirefox
import linux.LinuxDisplay

import akka.actor.{ Actor, Cancellable, PoisonPill, Props }
import net.selenate.common.comms.req._
import net.selenate.common.comms.res.SeCommsRes
import net.selenate.common.sessions.SessionRequest
import scala.concurrent.duration.Duration
import net.selenate.server.linux.DisplayInfo

object SessionActor {
  def props(sessionRequest: SessionRequest, d: SelenateFirefox) = Props(new SessionActor(sessionRequest, d))
}

class SessionActor(sessionRequest: SessionRequest, d: SelenateFirefox)
    extends Actor
    with Loggable {
  val sessionID  = sessionRequest.getSessionID
  val isRecorded: Boolean = sessionRequest.getIsRecorded

  override lazy val logPrefix = Some(sessionID)
  logInfo(s"""Session actor started""")

  private var keepaliveScheduler: Option[Cancellable] = None
  private def isKeepalive = keepaliveScheduler.isDefined
  implicit val actionContext = ActionContext(true)

  (isRecorded, d.displayInfo) match {
    case (true, Some(DisplayInfo(displayNum, _))) =>
      val filename = LinuxDisplay.record(sessionID, displayNum)
      logDebug(s"Recording session to: $filename")
    case _ =>
      logDebug("Session not recorded")
  }

  private def actionMan: PF[SeCommsReq, SeCommsRes] = {
    case arg: SeReqAddCookie          => new AddCookieAction         (sessionID, d).act(arg)
    case arg: SeReqAppendText         => new AppendTextAction        (sessionID, d).act(arg)
    case arg: SeReqCapture            => new CaptureAction           (sessionID, d).act(arg)
    case arg: SeReqCaptureElement     => new CaptureElementAction    (sessionID, d).act(arg)
    case arg: SeReqCaptureWindow      => new CaptureWindowAction     (sessionID, d).act(arg)
    case arg: SeReqClearText          => new ClearTextAction         (sessionID, d).act(arg)
    case arg: SeReqClick              => new ClickAction             (sessionID, d).act(arg)
    case arg: SeReqClose              => new CloseAction             (sessionID, d).act(arg)
    case arg: SeReqDeleteCookieNamed  => new DeleteCookieNamedAction (sessionID, d).act(arg)
    case arg: SeReqDestroySession     => new DestroySessionAction    (sessionID, d).act(arg)
    case arg: SeReqDownload           => new DownloadAction          (sessionID, d).act(arg)
    case arg: SeReqElementExists      => new ElementExistsAction     (sessionID, d).act(arg)
    case arg: SeReqExecuteScript      => new ExecuteScriptAction     (sessionID, d).act(arg)
    case arg: SeReqFindAlert          => new FindAlertAction         (sessionID, d).act(arg)
    case arg: SeReqFindAndClick       => new FindAndClickAction      (sessionID, d).act(arg)
    case arg: SeReqFindElement        => new FindElementAction       (sessionID, d).act(arg)
    case arg: SeReqFindElementList    => new FindElementListAction   (sessionID, d).act(arg)
    case arg: SeReqFindSelect         => new FindSelectAction        (sessionID, d).act(arg)
    case arg: SeReqGet                => new GetAction               (sessionID, d).act(arg)
    case arg: SeReqNavigateBack       => new NavigateBackAction      (sessionID, d).act(arg)
    case arg: SeReqNavigateForward    => new NavigateForwardAction   (sessionID, d).act(arg)
    case arg: SeReqNavigateRefresh    => new NavigateRefreshAction   (sessionID, d).act(arg)
    case arg: SeReqQuit               => new QuitAction              (sessionID, d).act(arg)
    case arg: SeReqResetFrame         => new ResetFrameAction        (sessionID, d).act(arg)
    case arg: SeReqSelectOption       => new SelectOptionAction      (sessionID, d).act(arg)
    case arg: SeReqSetUseFrames       => new SetUseFramesAction      (sessionID, d).act(arg)
    case arg: SeReqStartKeepalive     => new StartKeepaliveAction    (sessionID, d).act(arg)
    case arg: SeReqStopKeepalive      => new StopKeepaliveAction     (sessionID, d).act(arg)
    case arg: SeReqSwitchFrame        => new SwitchFrameAction       (sessionID, d).act(arg)
    case arg: SeReqSystemClick        => new SystemClickAction       (sessionID, d).act(arg)
    case arg: SeReqSystemInput        => new SystemInputAction       (sessionID, d).act(arg)
    case arg: SeReqWaitFor            => new WaitForAction           (sessionID, d).act(arg)
    case arg: SeReqWaitForBrowserPage => new WaitForBrowserPageAction(sessionID, d).act(arg)
  }

  private def receiveBase: Receive = {
    case "ping" =>
      sender ! "pong"
    case data @ KeepaliveData(delay, reqList) =>
      logTrace("Keepalive tick")
      data.reqList foreach actionMan
    case arg: SeReqDestroySession =>
      sender ! actionMan(arg)
      self ! PoisonPill
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
      val senderName = sender.path.toString
      try {
        logDebug(s"""Received $clazz from $senderName: ${ arg.toString }""")
        base.apply(arg)
      } catch {
        case e: Exception =>
          val msg = s"""An error occured while processing message $clazz from $senderName: ${ arg.toString }"""
          logWarn(msg, e)
          sender ! new Exception(e)
      }
    }
  }

  def receive = wrap(receiveBase)

  override def postStop() {
    d.quit()
    d.displayInfo foreach LinuxDisplay.destroy
    logInfo(s"""Session actor stopped""")
  }

  private def startKeepalive(data: KeepaliveData) {
    if (!isKeepalive) {
      logTrace("Starting keepalive.")
      keepaliveScheduler = Some(context.system.scheduler.schedule(Duration.Zero, data.delay, self, data))
    }
  }

  private def stopKeepalive() {
    if (isKeepalive) {
      logTrace("Stopping keepalive.")
      keepaliveScheduler.map(_.cancel)
      keepaliveScheduler = None
    }
  }
}
