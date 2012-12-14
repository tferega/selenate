package net.selenate.client.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.selenate.common.comms.*;
import net.selenate.common.comms.req.*;
import net.selenate.common.comms.res.*;
import net.selenate.common.user.BrowserPage;
import net.selenate.common.user.ElementSelectMethod;
import net.selenate.common.user.ElementSelector;
import net.selenate.common.user.IAlert;
import net.selenate.common.user.IBrowser;
import net.selenate.common.user.IElement;
import net.selenate.common.user.Location;
import net.selenate.common.user.Position;

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
    final SeResElement res = typedBlock(new SeReqElement(reqMethod, query), SeResElement.class);

    return resToUserElement(res);
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
    final SeResElementList res = typedBlock(new SeReqElementList(reqMethod, query), SeResElementList.class);

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



  private SeSelectMethod userToReqSelectMethod(final ElementSelectMethod userMethod) {
    final SeSelectMethod reqMethod;
    switch (userMethod) {
      case CLASS_NAME:        reqMethod = SeSelectMethod.CLASS_NAME;        break;
      case CSS_SELECTOR:      reqMethod = SeSelectMethod.CSS_SELECTOR;      break;
      case ID:                reqMethod = SeSelectMethod.ID;                break;
      case LINK_TEXT:         reqMethod = SeSelectMethod.LINK_TEXT;         break;
      case NAME:              reqMethod = SeSelectMethod.NAME;              break;
      case PARTIAL_LINK_TEXT: reqMethod = SeSelectMethod.PARTIAL_LINK_TEXT; break;
      case TAG_NAME:          reqMethod = SeSelectMethod.TAG_NAME;          break;
      case UUID:              reqMethod = SeSelectMethod.UUID;              break;
      case XPATH:             reqMethod = SeSelectMethod.XPATH;             break;
      default:                throw new RuntimeException("Unexpected error!");
    }

    return reqMethod;
  }

  private ActorElement resToUserElement(final SeResElement resElement) {
    return new ActorElement(
        session,
        resElement.uuid,
        new Position(resElement.posX, resElement.posY),
        new Location(resElement.width, resElement.height),
        resElement.name,
        resElement.text,
        resElement.isDisplayed,
        resElement.isEnabled,
        resElement.isSelected,
        resElement.attributeList,
        new ArrayList<IElement>());
  }

  private List<IElement> resToUserElementList(final List<SeResElement> resElementList) {
    final List<IElement> userElementList = new ArrayList<IElement>();
    for (final SeResElement resElement : resElementList) {
      final ActorElement actorElement = resToUserElement(resElement);
      userElementList.add(actorElement);
    }

    return userElementList;
  }

  private SeElementSelector userToReqSelector(final ElementSelector userSelector) {
    return new SeElementSelector(userToReqSelectMethod(userSelector.method), userSelector.query);
  }

  private List<SeElementSelector> userToReqSelectorList(final List<ElementSelector> userSelectorList) {
    final List<SeElementSelector> reqSelectorList = new ArrayList<SeElementSelector>();
    for (final ElementSelector userSelector : userSelectorList) {
      final SeElementSelector reqSelector = userToReqSelector(userSelector);
      reqSelectorList.add(reqSelector);
    }

    return reqSelectorList;
  }

  private List<SePage> userToReqPageList(final List<BrowserPage> userPageList) {
    final List<SePage> reqPageList = new ArrayList<SePage>();
    for (final BrowserPage userPage : userPageList) {
      final SePage reqPage = new SePage(userPage.name, userToReqSelectorList(userPage.selectorList));
      reqPageList.add(reqPage);
    }

    return reqPageList;
  }
}
