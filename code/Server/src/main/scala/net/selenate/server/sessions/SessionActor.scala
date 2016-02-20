package net.selenate.server
package sessions

import linux.LinuxDisplay
import akka.actor.{ Actor, Cancellable, PoisonPill, Props }
import scala.concurrent.duration.Duration
import net.selenate.server.linux.DisplayInfo
import net.selenate.common.sessions.SessionRequest
import net.selenate.common.comms.req._
import net.selenate.common.comms.res.SeCommsRes
import net.selenate.server.extensions.SelenateFirefox
import net.selenate.server.actions.SessionContext
import scala.concurrent.duration._
import scala.collection.JavaConversions._
import net.selenate.server.actions.workers._

object SessionActor {
  private case object KeepaliveTick
  def props(sessionRequest: SessionRequest, d: SelenateFirefox) = Props(new SessionActor(sessionRequest, d))
}

class SessionActor(sessionRequest: SessionRequest, d: SelenateFirefox)
    extends Actor
    with Loggable {
  import SessionActor._

  override lazy val logPrefix = Some(sessionID)
  private var keepaliveScheduler: Option[Cancellable] = None
  private def isKeepalive = keepaliveScheduler.isDefined
  private val sessionID  = sessionRequest.getSessionID
  private val sessionContext = SessionContext.default

  logInfo(s"""Session actor started""")

  private def actionMan: PF[SeCommsReq, SeCommsRes] = {
    case arg: SeReqBrowserCapture        => new BrowserCaptureAction        (sessionID, sessionContext, d).act(arg)
    case arg: SeReqBrowserQuit           => new BrowserQuitAction           (sessionID, sessionContext, d).act(arg)
    case arg: SeReqBrowserWaitFor        => new BrowserWaitForAction        (sessionID, sessionContext, d).act(arg)
    case arg: SeReqCaptchaBreak          => new CaptchaBreakAction          (sessionID, sessionContext, d).act(arg)
    case arg: SeReqCookieAdd             => new CookieAddAction             (sessionID, sessionContext, d).act(arg)
    case arg: SeReqCookieDeleteNamed     => new CookieDeleteNamedAction     (sessionID, sessionContext, d).act(arg)
    case arg: SeReqElementCapture        => new ElementCaptureAction        (sessionID, sessionContext, d).act(arg)
    case arg: SeReqElementClick          => new ElementClickAction          (sessionID, sessionContext, d).act(arg)
    case arg: SeReqElementFindList       => new ElementFindListAction       (sessionID, sessionContext, d).act(arg)
    case arg: SeReqElementGetAttributes  => new ElementGetAttributesAction  (sessionID, sessionContext, d).act(arg)
    case arg: SeReqElementTextInput      => new ElementTextInputAction      (sessionID, sessionContext, d).act(arg)
    case arg: SeReqScriptExecute         => new ScriptExecuteAction         (sessionID, sessionContext, d).act(arg)
    case arg: SeReqSelectChoose          => new SelectChooseAction          (sessionID, sessionContext, d).act(arg)
    case arg: SeReqSelectFindList        => new SelectFindListAction        (sessionID, sessionContext, d).act(arg)
    case arg: SeReqSelectFindOptionList  => new SelectFindOptionListAction  (sessionID, sessionContext, d).act(arg)
    case arg: SeReqSessionDestroy        => new SessionDestroyAction        (sessionID, sessionContext, d).act(arg)
    case arg: SeReqSessionDownload       => new SessionDownloadAction       (sessionID, sessionContext, d).act(arg)
    case arg: SeReqSessionSetContext     => new SessionSetContextAction     (sessionID, sessionContext, d).act(arg)
    case arg: SeReqSessionStartKeepalive => new SessionStartKeepaliveAction (sessionID, sessionContext, d).act(arg)
    case arg: SeReqSessionStopKeepalive  => new SessionStopKeepaliveAction  (sessionID, sessionContext, d).act(arg)
    case arg: SeReqSystemClick           => new SystemClickAction           (sessionID, sessionContext, d).act(arg)
    case arg: SeReqSystemInput           => new SystemInputAction           (sessionID, sessionContext, d).act(arg)
    case arg: SeReqWindowCapture         => new WindowCaptureAction         (sessionID, sessionContext, d).act(arg)
    case arg: SeReqWindowClose           => new WindowCloseAction           (sessionID, sessionContext, d).act(arg)
    case arg: SeReqWindowFrameReset      => new WindowFrameResetAction      (sessionID, sessionContext, d).act(arg)
    case arg: SeReqWindowFrameSwitch     => new WindowFrameSwitchAction     (sessionID, sessionContext, d).act(arg)
    case arg: SeReqWindowGet             => new WindowGetAction             (sessionID, sessionContext, d).act(arg)
    case arg: SeReqWindowNavigate        => new WindowNavigateAction        (sessionID, sessionContext, d).act(arg)
  }

  private def receiveBase: Receive = {
    case "ping" =>
      sender ! "pong"
    case KeepaliveTick =>
      logTrace("Keepalive tick")
      sessionContext.keepaliveReqList foreach actionMan
    case arg: SeReqSessionDestroy =>
      sender ! actionMan(arg)
      self ! PoisonPill
    case arg: SeReqSessionStartKeepalive =>
      sender ! actionMan(arg)
      startKeepalive()
    case arg: SeReqSessionSetContext =>
      setContext(arg)
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

  private def startKeepalive() {
    if (!isKeepalive) {
      logTrace("Starting keepalive.")
      keepaliveScheduler = Some(context.system.scheduler.schedule(Duration.Zero, sessionContext.keepaliveDelay, self, KeepaliveTick))
    }
  }

  private def stopKeepalive() {
    if (isKeepalive) {
      logTrace("Stopping keepalive.")
      keepaliveScheduler.map(_.cancel)
      keepaliveScheduler = None
    }
  }

  private def setContext(newContext: SeReqSessionSetContext) {
    logInfo("Setting context to: " + newContext);

    Option(newContext.isUseFrames).foreach(e =>
      sessionContext.useFrames = e)

    Option(newContext.persistentPresentSelectorList).foreach(e =>
      sessionContext.persistentPresentSelectorList = e.toIndexedSeq)

      Option(newContext.persistentAbsentSelectorList).foreach(e =>
      sessionContext.persistentAbsentSelectorList = e.toIndexedSeq)

    Option(newContext.keepaliveDelayMillis).foreach(e =>
      sessionContext.keepaliveDelay = (e: Long).milliseconds)

    Option(newContext.keepaliveReqList).foreach(e =>
      sessionContext.keepaliveReqList  = e.toIndexedSeq)

    Option(newContext.waitTimeout).foreach(e =>
      sessionContext.waitTimeout  = e)

    Option(newContext.waitResolution).foreach(e =>
      sessionContext.waitResolution  = e)

    Option(newContext.waitDelay).foreach(e =>
      sessionContext.waitDelay  = e)
  }
}
