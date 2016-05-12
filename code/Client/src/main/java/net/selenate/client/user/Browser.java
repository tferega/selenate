package net.selenate.client.user;

import akka.actor.ActorRef;
import akka.util.Timeout;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import net.selenate.common.comms.*;
import net.selenate.common.comms.req.*;
import net.selenate.common.comms.res.*;

public class Browser extends ActorBase {
  public Browser(
      final Timeout timeout,
      final ActorRef session) {
    super(timeout, session);
  }

  public void contextSetPersistentSelectors(
      final List<SeElementSelector> presentSelectorList,
      final List<SeElementSelector> absentSelectorList) throws IOException {
    final SeReqSessionSetContext sessionContext = SeReqSessionSetContext.empty
        .withPersistentPresentSelectorList(presentSelectorList)
        .withPersistentAbsentSelectorList(absentSelectorList);
    setSessionContext(sessionContext);
  }

  public void contextSetWaitDelay(final long waitDelay) throws IOException {
    final SeReqSessionSetContext sessionContext = SeReqSessionSetContext.empty
        .withWaitDelay(waitDelay);
    setSessionContext(sessionContext);
  }

  public void open(final String url) throws IOException {
    typedBlock(new SeReqWindowGet(url), SeResWindowGet.class);
  }

  public void back() throws IOException {
    typedBlock(new SeReqWindowNavigate(SeNavigateDirection.BACK), SeResWindowNavigate.class);
  }

  public SeResBrowserCapture capture() throws IOException {
    final SeResBrowserCapture res = typedBlock(new SeReqBrowserCapture("capture", true), SeResBrowserCapture.class);
    return res;
  }

  public Set<SeCookie> getCookieSet() throws IOException {
    final SeResBrowserCapture capture = capture();
    return capture.getWindowList().get(0).getCookieSet();
  }

  public SeCookie getCookie(final String name) throws IOException {
    return getCookieSet()
        .stream()
        .filter(e -> name.equals(e.getName()))
        .findFirst()
        .orElseThrow(() -> new IOException(String.format("Required cookie with name %s was not found!", name)));
  }

  public String breakCaptcha(final SeElementSelector element) throws IOException {
    final SeResCaptchaBreak res = typedBlock(new SeReqCaptchaBreak(element), SeResCaptchaBreak.class);
    return res.getText();
  }

  public String getHtml() throws IOException {
    return capture().getWindowList().get(0).getHtml();
  }

  public byte[] download(final String url) throws IOException {
    final SeResSessionDownload res = typedBlock(new SeReqSessionDownload(url, Collections.emptyList()), SeResSessionDownload.class);
    return res.getBody();
  }

  public Element tryElement(final SeElementSelector selector) throws IOException {
    final List<Element> elements = findElementList(selector);
    if (elements.size() > 0) {
      return elements.get(0);
    } else {
      return null;
    }
  }

  public Element getElement(final SeElementSelector selector) throws IOException {
    final List<Element> elements = findElementList(selector);
    if (elements.size() > 0) {
      return elements.get(0);
    } else {
      throw new IOException(String.format("Required element with selector %s was not found!", selector));
    }
  }

  public int elementCount(final SeElementSelector selector) throws IOException {
    return findElementList(selector).size();
  }

  public boolean elementExists(final SeElementSelector selector) throws IOException {
    return findElementList(selector).size() > 0;
  }

  public String executeScript(final String javascript) throws IOException {
    final SeResScriptExecute res = typedBlock(new SeReqScriptExecute(javascript), SeResScriptExecute.class);
    return res.getResult();
  }

  public List<String> executeElemFunScript(
      final String cssSelector,
      final String elementFunction) throws IOException {
    final List<List<String>> result = executeElemFunScript(cssSelector, Arrays.asList(elementFunction));
    return result.stream()
        .flatMap(c -> c.stream())
        .collect(Collectors.toList());
  }

  public List<List<String>> executeElemFunScript(
        final String cssSelector,
        final List<String> elementFunctionList) throws IOException {
    final String dellimiter = "#DELLIMITER#";
    final String sublimiter = "#SUBLIMITER#";
    final String jsTemplate = ""
      + "var query = document.querySelectorAll('%s');\n"
      + "function f(e, i) {\n"
      + "  return %s;\n"
      + "}\n"
      + "return Array.prototype.slice.call(query).map(f).join('%s');";

    final String elementFunction = String.join("+'" + sublimiter + "'+\n  ", elementFunctionList);
    final String js = String.format(jsTemplate, cssSelector, elementFunction, dellimiter);
    final String result = executeScript(js);
    if (result.isEmpty()) {
      return new ArrayList<List<String>>();
    } else {
      return Arrays.asList(result.split(dellimiter))
          .stream()
          .map(row -> Arrays.asList(row.split(sublimiter)))
          .collect(Collectors.toList());
    }
  }

  public String waitFor(List<SePage> pageList) throws IOException {
    final SeResBrowserWaitFor res = typedBlock(new SeReqBrowserWaitFor(pageList), SeResBrowserWaitFor.class);
    return res.IsSuccessful() ? res.getFoundPage().getName() : null;
  }

  public void quit() throws IOException {
    typedBlock(new SeReqBrowserQuit(), SeResBrowserQuit.class);
    typedBlock(new SeReqSessionDestroy(), SeResSessionDestroy.class);
  }


  private List<Element> findElementList(final SeElementSelector selector) throws IOException {
    final SeResElementFindList res = typedBlock(new SeReqElementFindList(selector), SeResElementFindList.class);
    final List<SeElement> foundElements = res.getElementList();
    final List<Element> actorElements = new ArrayList<>();
    for (int i = 0; i < foundElements.size(); i++) {
      final Element actorElement = new Element(timeout, session, foundElements.get(i));
      actorElements.add(actorElement);
    }
    return actorElements;
  }

  private void setSessionContext(final SeReqSessionSetContext sessionContext) throws IOException {
    typedBlock(sessionContext, SeResSessionSetContext.class);
  }
}
