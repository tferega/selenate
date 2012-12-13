package net.selenate.client.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.selenate.common.comms.req.*;
import net.selenate.common.comms.res.*;
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
  public boolean waitFor(final List<ElementSelector> selector) throws IOException {
    final SeResWaitFor res = typedBlock(new SeReqWaitFor(userToReqSelectorList(selector)), SeResWaitFor.class);
    return res.isSuccessful;
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
    final SeReqSelectMethod reqMethod = userToReqSelectMethod(method);
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
    final SeReqSelectMethod reqMethod = userToReqSelectMethod(method);
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



  private SeReqSelectMethod userToReqSelectMethod(final ElementSelectMethod userMethod) {
    final SeReqSelectMethod reqMethod;
    switch (userMethod) {
      case CLASS_NAME:        reqMethod = SeReqSelectMethod.CLASS_NAME;        break;
      case CSS_SELECTOR:      reqMethod = SeReqSelectMethod.CSS_SELECTOR;      break;
      case ID:                reqMethod = SeReqSelectMethod.ID;                break;
      case LINK_TEXT:         reqMethod = SeReqSelectMethod.LINK_TEXT;         break;
      case NAME:              reqMethod = SeReqSelectMethod.NAME;              break;
      case PARTIAL_LINK_TEXT: reqMethod = SeReqSelectMethod.PARTIAL_LINK_TEXT; break;
      case TAG_NAME:          reqMethod = SeReqSelectMethod.TAG_NAME;          break;
      case UUID:              reqMethod = SeReqSelectMethod.UUID;              break;
      case XPATH:             reqMethod = SeReqSelectMethod.XPATH;             break;
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

  private SeReqElementSelector userToReqSelector(final ElementSelector userSelector) {
    return new SeReqElementSelector(userToReqSelectMethod(userSelector.method), userSelector.query);
  }

  private List<SeReqElementSelector> userToReqSelectorList(final List<ElementSelector> userSelectorList) {
    final List<SeReqElementSelector> reqSelectorList = new ArrayList<SeReqElementSelector>();
    for (final ElementSelector userSelector : userSelectorList) {
      final SeReqElementSelector reqSelector = userToReqSelector(userSelector);
      reqSelectorList.add(reqSelector);
    }

    return reqSelectorList;
  }
}
