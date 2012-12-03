package net.selenate.client.user;

import java.io.IOException;

import net.selenate.common.comms.req.*;
import net.selenate.common.comms.res.*;
import net.selenate.common.user.ElementSelector;
import net.selenate.common.user.IAlert;
import net.selenate.common.user.IBrowser;
import net.selenate.common.user.IElement;

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
      Future<Object> future = Patterns.ask(session, req, timeout);
      Object result = Await.result(future, timeout.duration());
      return result;
    } catch (Exception e) {
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
    } catch (ClassCastException e) {
      throw new IOException(String.format("Received an unexpected response! Found: %s; required: %s.", obj.getClass().toString(), clazz.toString()), e);
    }
  }


  @Override
  public void open(String url) throws IOException {
    typedBlock(new SeReqGet(url), SeResGet.class);
  }

  @Override
  public void capture(String name) throws IOException {
    typedBlock(new SeReqCapture(name), SeResCapture.class);
  }

  @Override
  public void executeScript(String javascript) throws IOException {
    throw new IllegalArgumentException("Not supported");
  }

  @Override
  public void quit() throws IOException {
    typedBlock(new SeReqQuit(), SeResQuit.class);

  }

  @Override
  public IElement tryGetElement(ElementSelector method, String query)
      throws IOException {
    throw new IllegalArgumentException("Not supported");
  }

  @Override
  public boolean isAlert() throws IOException {
    throw new IllegalArgumentException("Not supported");
  }

  @Override
  public IAlert getAlert() throws IOException {
    throw new IllegalArgumentException("Not supported");
  }
}
