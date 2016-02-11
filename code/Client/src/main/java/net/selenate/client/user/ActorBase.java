package net.selenate.client.user;

import akka.actor.ActorRef;
import akka.pattern.Patterns;
import akka.util.Timeout;
import java.io.IOException;
import scala.concurrent.Await;
import scala.concurrent.Future;

class ActorBase {
  protected final Timeout timeout;
  protected final ActorRef session;

  public ActorBase(
      final Timeout timeout,
      final ActorRef session) {
    this.timeout = timeout;
    this.session = session;
    validate();
  }

  private void validate() {
    if(session == null && timeout == null) {
      throw new IllegalArgumentException("Session and timeout cannot be null!");
    }
    if(session == null) {
      throw new IllegalArgumentException("Session cannot be null!");
    }
    if(timeout == null) {
      throw new IllegalArgumentException("Timeout cannot be null!");
    }
  }

  protected Object block(final Object req) throws IOException {
    try {
      final Future<Object> future = Patterns.ask(session, req, timeout);
      final Object result = Await.result(future, timeout.duration());
      return result;
    } catch (final Exception e) {
      throw new IOException(String.format("An error occurred while sending the message to remote actor!\nMessage: %s", req.toString()), e);
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
}
