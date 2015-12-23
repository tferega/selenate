package net.selenate.client.user;

import akka.actor.ActorRef;
import akka.pattern.Patterns;
import java.io.IOException;
import net.selenate.client.C;
import scala.concurrent.Await;
import scala.concurrent.Future;

class ActorBase {
  protected final ActorRef session;

  public ActorBase(final ActorRef session) {
    if (session == null) {
      throw new IllegalArgumentException("Session cannot be null!");
    }

    this.session = session;
  }

  protected Object block(final Object req) throws IOException {
    try {
      final Future<Object> future = Patterns.ask(session, req, C.Global.Timeouts);
      final Object result = Await.result(future, C.Global.Timeouts.duration());
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
}
