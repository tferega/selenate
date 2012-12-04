package net.selenate.client.user;

import java.io.IOException;

import net.selenate.common.comms.req.*;
import net.selenate.common.comms.res.*;
import net.selenate.common.user.ElementSelector;
import net.selenate.common.user.IAlert;
import net.selenate.common.user.IBrowser;
import net.selenate.common.user.IElement;
import net.selenate.common.user.Location;
import net.selenate.common.user.Position;

import akka.actor.ActorRef;
import akka.dispatch.Await;
import akka.dispatch.Future;
import akka.pattern.Patterns;
import akka.util.Timeout;
import java.util.concurrent.TimeUnit;

public class ActorBrowser implements IBrowser {
  protected static final Timeout timeout = new Timeout(5, TimeUnit.SECONDS);
  protected final ActorRef session;

  public ActorBrowser(final ActorRef session) {
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
    } catch (final ClassCastException e) {
      throw new IOException(String.format("Received an unexpected response! Found: %s; required: %s.", obj.getClass().toString(), clazz.toString()), e);
    }
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
  public void quit() throws IOException {
    typedBlock(new SeReqQuit(), SeResQuit.class);
  }

  @Override
  public IElement findElement(
      final ElementSelector method,
      final String query)
      throws IOException {
    final SeReqSelectMethod reqMethod;
    switch (method) {
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

    final SeResElement res = typedBlock(new SeReqElement(reqMethod, query), SeResElement.class);

    return new ActorElement(
        session,
        res.uuid,
        new Position(res.posX, res.posY),
        new Location(res.width, res.height),
        res.name,
        res.text,
        res.isDisplayed,
        res.isEnabled,
        res.isSelected,
        res.attributeList,
        null);
  }

  @Override
  public boolean isAlert() throws IOException {
    throw new IllegalArgumentException("Not supported");
  }

  @Override
  public IAlert findAlert() throws IOException {
    final SeResFindAlert alert = typedBlock(new SeReqFindAlert(), SeResFindAlert.class);
    // Must first create an implementation.
    throw new IllegalArgumentException("Not supported");
  }
}
