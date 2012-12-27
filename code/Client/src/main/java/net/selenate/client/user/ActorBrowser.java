package net.selenate.client.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.selenate.common.comms.*;
import net.selenate.common.comms.req.*;
import net.selenate.common.comms.res.*;
import net.selenate.common.user.*;

import akka.actor.ActorRef;

public class ActorBrowser extends ActorBase implements IBrowser {
  public ActorBrowser(final ActorRef session) {
    super(session);
  }

  @Override
  public void open(final String url) throws IOException {
    typedBlock(new SeReqGet(url), SeResGet.class);
  }

  @Override
  public void capture(final String name) throws IOException {
    typedBlock(new SeReqCapture(name), SeResCapture.class);
  }

  @Override
  public String executeScript(final String javascript) throws IOException {
    final SeResExecuteScript res = typedBlock(new SeReqExecuteScript(javascript), SeResExecuteScript.class);
    return res.result;
  }

  @Override
  public void resetFrame() throws IOException {
    typedBlock(new SeReqResetFrame(), SeResResetFrame.class);
  }

  @Override
  public void switchFrame(final int frame) throws IOException {
    typedBlock(new SeReqSwitchFrame(frame), SeResSwitchFrame.class);
  }

  @Override
  public boolean waitFor(final List<ElementSelector> selectorList) throws IOException {
    return waitFor(new BrowserPage("default", selectorList));
  }

  @Override
  public boolean waitFor(final BrowserPage page) throws IOException {
    final List<BrowserPage> pageList = new ArrayList<BrowserPage>();
    pageList.add(page);
    String res = waitForAny(pageList);
    return (res != null);
  }

  @Override
  public String waitForAny(BrowserPage ... pageList) throws IOException {
    return waitForAny(Arrays.asList(pageList));
  }

  @Override
  public String waitForAny(final List<BrowserPage> pageList) throws IOException {
    final SeResWaitFor res = typedBlock(new SeReqWaitFor(userToReqPageList(pageList)), SeResWaitFor.class);
    return res.isSuccessful ? res.foundName : null;
  }

  @Override
  public void quit() throws IOException {
    typedBlock(new SeReqQuit(), SeResQuit.class);
  }

  @Override
  public IElement findElement(
      final ElementSelectMethod method,
      final String query)
      throws IOException {
    final SeSelectMethod reqMethod = userToReqSelectMethod(method);
    final SeResFindElement res = typedBlock(new SeReqFindElement(reqMethod, query), SeResFindElement.class);

    return resToUserElement(res.element);
  }

  @Override
  public IElement findElement(final ElementSelector selector) throws IOException {
    return findElement(selector.method, selector.query);
  }

  @Override
  public List<IElement> findElementList(
      final ElementSelectMethod method,
      final String query)
          throws IOException {
    final SeSelectMethod reqMethod = userToReqSelectMethod(method);
    final SeResFindElementList res = typedBlock(new SeReqFindElementList(reqMethod, query), SeResFindElementList.class);

    return resToUserElementList(res.elementList);
  }

  @Override
  public List<IElement> findElementList(final ElementSelector selector) throws IOException {
    return findElementList(selector.method, selector.query);
  }

  @Override
  public boolean isAlert() throws IOException {
    throw new IllegalArgumentException("Not supported");
  }

  @Override
  public IAlert findAlert() throws IOException {
    final SeResFindAlert alert = typedBlock(new SeReqFindAlert(), SeResFindAlert.class);
    return new ActorAlert(session, alert.text);
  }
}
