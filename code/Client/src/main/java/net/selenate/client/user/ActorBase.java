package net.selenate.client.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import net.selenate.common.comms.*;
import net.selenate.common.user.*;

import akka.actor.ActorRef;
import akka.dispatch.Await;
import akka.dispatch.Future;
import akka.pattern.Patterns;
import akka.util.Timeout;

public abstract class ActorBase {
  protected static final Timeout timeout = new Timeout(30, TimeUnit.SECONDS);
  protected final ActorRef session;

  public ActorBase(final ActorRef session) {
    if (session == null) {
      throw new IllegalArgumentException("Session cannot be null!");
    }

    this.session = session;
  }

  protected Object block(final Object req) throws IOException {
    try {
      final Future<Object> future = Patterns.ask(session, req, timeout);
      final Object result = Await.result(future, timeout.duration());
      return result;
    } catch (final Exception e) {
      throw new IOException(String.format("An error occured while sending the message to remote actor!\nMessage: %s", req.toString()), e);
    }
  }

  protected <T> T typedBlock(
      final Object req,
      final Class<T> clazz) throws IOException {
    final Object obj = block(req);
    try {
      final T res = clazz.cast(obj);
      return res;
    } catch (final ClassCastException e0) {
      try {
        final Exception ex = Exception.class.cast(obj);
        throw new IOException("Received an exception!", ex);
      } catch (final ClassCastException e1) {
        throw new IOException(String.format("Received an unexpected response! Found: %s; required: %s.", obj.getClass().toString(), clazz.toString()), e0);
      }
    }
  }





  protected SeElementSelectMethod userToReqSelectMethod(final ElementSelectMethod userMethod) {
    final SeElementSelectMethod reqMethod;
    switch (userMethod) {
      case CLASS_NAME:        reqMethod = SeElementSelectMethod.CLASS_NAME;        break;
      case CSS_SELECTOR:      reqMethod = SeElementSelectMethod.CSS_SELECTOR;      break;
      case ID:                reqMethod = SeElementSelectMethod.ID;                break;
      case LINK_TEXT:         reqMethod = SeElementSelectMethod.LINK_TEXT;         break;
      case NAME:              reqMethod = SeElementSelectMethod.NAME;              break;
      case PARTIAL_LINK_TEXT: reqMethod = SeElementSelectMethod.PARTIAL_LINK_TEXT; break;
      case TAG_NAME:          reqMethod = SeElementSelectMethod.TAG_NAME;          break;
      case UUID:              reqMethod = SeElementSelectMethod.UUID;              break;
      case XPATH:             reqMethod = SeElementSelectMethod.XPATH;             break;
      default:                throw new RuntimeException("Unexpected error!");
    }

    return reqMethod;
  }

  protected ActorElement resToUserElement(final SeElement resElement) {
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
        resElement.framePath,
        resElement.attributeList,
        new ArrayList<IElement>());
  }

  protected List<IElement> resToUserElementList(final List<SeElement> resElementList) {
    final List<IElement> userElementList = new ArrayList<IElement>();
    for (final SeElement resElement : resElementList) {
      final ActorElement actorElement = resToUserElement(resElement);
      userElementList.add(actorElement);
    }

    return userElementList;
  }

  protected SeElementSelector userToReqSelector(final ElementSelector userSelector) {
    return new SeElementSelector(userToReqSelectMethod(userSelector.method), userSelector.query);
  }

  protected List<SeElementSelector> userToReqSelectorList(final List<ElementSelector> userSelectorList) {
    final List<SeElementSelector> reqSelectorList = new ArrayList<SeElementSelector>();
    for (final ElementSelector userSelector : userSelectorList) {
      final SeElementSelector reqSelector = userToReqSelector(userSelector);
      reqSelectorList.add(reqSelector);
    }

    return reqSelectorList;
  }

  protected List<SePage> userToReqPageList(final List<BrowserPage> userPageList) {
    final List<SePage> reqPageList = new ArrayList<SePage>();
    for (final BrowserPage userPage : userPageList) {
      final SePage reqPage = new SePage(userPage.name, userToReqSelectorList(userPage.selectorList));
      reqPageList.add(reqPage);
    }

    return reqPageList;
  }

  protected ActorSelect resToUserSelect(final SeSelect resSelect) {
    return new ActorSelect(
        session,
        resToUserElement(resSelect.element),
        resSelect.optionCount,
        resSelect.selectedIndex,
        resToUserOption(resSelect.selectedOption),
        resToUserOptionList(resSelect.options));
  }

  protected ActorOption resToUserOption(final SeOption resOption) {
    if (resOption == null) {
      return null;
    } else {
      return new ActorOption(
          session,
          resToUserElement(resOption.element));
    }
  }

  protected List<IOption> resToUserOptionList(final List<SeOption> resOptionList) {
    final List<IOption> userOptionList = new ArrayList<IOption>();
    for (final SeOption resOption : resOptionList) {
      final ActorOption actorOption = resToUserOption(resOption);
      userOptionList.add(actorOption);
    }

    return userOptionList;
  }
}
