package net.selenate.client.user;

import akka.actor.ActorRef;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.selenate.common.comms.*;
import net.selenate.common.comms.req.*;
import net.selenate.common.comms.res.*;

public class ActorBrowser extends ActorBase {
  public ActorBrowser(final ActorRef session) {
    super(session);
  }

  public void open(final String url) throws IOException {
    typedBlock(new SeReqWindowGet(url), SeResWindowGet.class);
  }

  public SeResBrowserCapture capture(final String name) throws IOException {
    final SeResBrowserCapture res = typedBlock(new SeReqBrowserCapture(name, true), SeResBrowserCapture.class);
    return res;
  }

  public String executeScript(final String javascript) throws IOException {
    final SeResScriptExecute res = typedBlock(new SeReqScriptExecute(javascript), SeResScriptExecute.class);
    return res.getResult();
  }

  public void quit() throws IOException {
    typedBlock(new SeReqBrowserQuit(), SeResBrowserQuit.class);
    typedBlock(new SeReqSessionDestroy(), SeResSessionDestroy.class);
  }

  public boolean elementExists(final SeElementSelector selector) throws IOException {
    final List<ActorElement> elements = findElementList(selector);
    return elements.size() > 0;
  }

  public ActorElement findElement(final SeElementSelector selector) throws IOException {
    final List<ActorElement> elements = findElementList(selector);
    if (elements.size() > 0) {
      return elements.get(0);
    } else {
      return null;
    }
  }

  public List<ActorElement> findElementList(final SeElementSelector selector) throws IOException {
    final SeResElementFindList res = typedBlock(new SeReqElementFindList(selector), SeResElementFindList.class);
    final List<SeElement> foundElements = res.getElementList();
    final List<ActorElement> actorElements = new ArrayList<>();
    for (int i = 0; i < foundElements.size(); i++) {
      final ActorElement actorElement = new ActorElement(session, foundElements.get(i));
      actorElements.add(actorElement);
    }
    return actorElements;
  }

  public void deleteCookieNamed(final String name) throws IOException {
    typedBlock(new SeReqCookieDeleteNamed(name), SeResCookieDeleteNamed.class);
  }

  public void addCookie(final SeCookie cookie) throws IOException {
    typedBlock(new SeReqCookieAdd(cookie), SeResCookieAdd.class);
  }

  public void navigate(final SeNavigateDirection direction) throws IOException {
    typedBlock(new SeReqWindowNavigate(direction), SeResWindowNavigate.class);
  }

  public byte[] download(final String url) throws IOException {
    final SeResSessionDownload res = typedBlock(new SeReqSessionDownload(url), SeResSessionDownload.class);
    return res.getBody();
  }

  public void startKeepalive() throws IOException {
    typedBlock(new SeReqSessionStartKeepalive(), SeResSessionStartKeepalive.class);
  }

  public void stopKeepalive() throws IOException {
    typedBlock(new SeReqSessionStopKeepalive(), SeResSessionStopKeepalive.class);
  }

  public SePage waitFor(final List<SePage> pageList) throws IOException {
    final SeResBrowserWaitFor res = typedBlock(new SeReqBrowserWaitFor(pageList), SeResBrowserWaitFor.class);
    return res.IsSuccessful() ? res.getFoundPage() : null;
  }

  public void systemClick(final int x, final int y) throws IOException {
    typedBlock(new SeReqSystemClick(x, y), SeResSystemClick.class);
  }

  public void systemInput(final String input) throws IOException {
    typedBlock(new SeReqSystemInput(input), SeResSystemInput.class);
  }

  public void contextSetUseFrames(final boolean useFrames) throws IOException {
    final SeReqSessionSetContext context = SeReqSessionSetContext.empty
        .withUseFrames(useFrames);
    setSessionContext(context);
  }

  public void contextSetPersistentSelectors(
      final List<SeElementSelector> presentSelectorList,
      final List<SeElementSelector> absentSelectorList) throws IOException {
    final SeReqSessionSetContext context = SeReqSessionSetContext.empty
        .withPersistentPresentSelectorList(presentSelectorList)
        .withPersistentAbsentSelectorList(absentSelectorList);
    setSessionContext(context);
  }

  public void contextSetKeepalive(
      final long delayMillis,
      final List<SeCommsReq> reqList) throws IOException {
    final SeReqSessionSetContext context = SeReqSessionSetContext.empty
        .withKeepaliveDelayMillis(delayMillis)
        .withKeepaliveReqList(reqList);
    setSessionContext(context);
  }

  public void contextSetWaitDelay(final long waitDelay) throws IOException {
    final SeReqSessionSetContext context = SeReqSessionSetContext.empty
        .withWaitDelay(waitDelay);
    setSessionContext(context);
  }

  private void setSessionContext(final SeReqSessionSetContext context) throws IOException {
    typedBlock(context, SeResSessionSetContext.class);
  }
}
