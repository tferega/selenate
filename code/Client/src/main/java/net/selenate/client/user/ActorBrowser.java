package net.selenate.client.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.selenate.common.comms.*;
import net.selenate.common.comms.req.*;
import net.selenate.common.comms.res.*;
import akka.actor.ActorRef;

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

  public boolean waitFor(final List<SeElementSelector> selectorList) throws IOException {
    return waitFor(new SePage("default", selectorList));
  }

  public boolean waitFor(final SeElementSelector... selectorList) throws IOException {
    return waitFor(new SePage("default", new ArrayList<SeElementSelector>(Arrays.asList(selectorList))));
  }

  public boolean waitFor(final SePage page) throws IOException {
    final List<SePage> pageList = new ArrayList<SePage>();
    pageList.add(page);
    String res = waitForAny(pageList);
    return (res != null);
  }

  public String waitForAny(SePage ... pageList) throws IOException {
    return waitForAny(Arrays.asList(pageList));
  }

  public String waitForAny(final List<SePage> pageList) throws IOException {
    final SePage resPage = waitForAnyPage(pageList);
    if(resPage == null) return null;
    return resPage.getName();
  }

  public void quit() throws IOException {
    typedBlock(new SeReqBrowserQuit(), SeResBrowserQuit.class);
    typedBlock(new SeReqSessionDestroy(), SeResSessionDestroy.class);
  }

  public boolean elementExists(final SeElementSelectMethod method, final String query) throws IOException {
    final SeElementSelector selector = new SeElementSelector(/*Optional.empty(), */method, query);
    return elementExists(selector);
  }

  public boolean elementExists(final SeElementSelector selector) throws IOException {
    final List<ActorElement> elements = findElementList(selector);
    return elements.size() > 0;
  }

  public ActorElement findElement(final SeElementSelectMethod method, final String query) throws IOException {
    final SeElementSelector selector = new SeElementSelector(/*Optional.empty(), */method, query);
    return findElement(selector);
  }

  public ActorElement findElement(final SeElementSelector selector) throws IOException {
    final List<ActorElement> elements = findElementList(selector);
    if (elements.size() > 0) {
      return elements.get(0);
    } else {
      return null;
    }
  }

  public List<ActorElement> findElementList(
      final SeElementSelectMethod method,
      final String query) throws IOException {
    final SeElementSelector selector = new SeElementSelector(/*Optional.empty(), */method, query);
    return findElementList(selector);
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

  public void deleteCookieNamed(String name) throws IOException {
    typedBlock(new SeReqCookieDeleteNamed(name), SeResCookieDeleteNamed.class);
  }

  public void addCookie(SeCookie cookie) throws IOException {
    typedBlock(new SeReqCookieAdd(cookie), SeResCookieAdd.class);
  }

  public void navigate(SeNavigateDirection direction) throws IOException {
    typedBlock(new SeReqWindowNavigate(direction), SeResWindowNavigate.class);
  }

  public byte[] download(String url) throws IOException {
    final SeResSessionDownload res = typedBlock(new SeReqSessionDownload(url), SeResSessionDownload.class);
    return res.getBody();
  }

  public void startKeepalive() throws IOException {
    typedBlock(new SeReqSessionStartKeepalive(), SeResSessionStartKeepalive.class);
  }

  public void stopKeepalive() throws IOException {
    typedBlock(new SeReqSessionStopKeepalive(), SeResSessionStopKeepalive.class);
  }

  public SePage waitForAnyPage(SePage... pageList) throws IOException {
    return waitForAnyPage(Arrays.asList(pageList));
  }

  private SePage waitForAnyPage(List<SePage> pageList) throws IOException {
    final SeResBrowserWaitFor res = typedBlock(new SeReqBrowserWaitFor(pageList), SeResBrowserWaitFor.class);
    return res.IsSuccessful() ? res.getFoundPage() : null;
  }

  public void systemClick(final int x, final int y) throws IOException {
    typedBlock(new SeReqSystemClick(x, y), SeResSystemClick.class);
  }

  public void systemInput(final String input) throws IOException {
    typedBlock(new SeReqSystemInput(input), SeResSystemInput.class);
  }
}
