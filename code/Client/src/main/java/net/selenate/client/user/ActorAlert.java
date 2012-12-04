package net.selenate.client.user;

import java.io.IOException;

import akka.actor.ActorRef;

import net.selenate.common.user.IAlert;

public class ActorAlert implements IAlert {
  protected final ActorRef session;

  private final String text;

  public ActorAlert(
      final ActorRef session,
      final String   text) {
    if (session == null) {
      throw new IllegalArgumentException("Session cannot be null!");
    }
    if (text == null) {
      throw new IllegalArgumentException("Text cannot be null");
    }

    this.session = session;
    this.text    = text;
  }

  @Override
  public void accept() throws IOException {
    throw new IllegalArgumentException("Not supported");
  }

  @Override
  public void dismiss() throws IOException {
    throw new IllegalArgumentException("Not supported");
  }

  @Override
  public String getText() {
    return text;
  }

  @Override
  public void setText() throws IOException {
    throw new IllegalArgumentException("Not supported");
  }

}
