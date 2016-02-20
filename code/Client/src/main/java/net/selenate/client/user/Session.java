package net.selenate.client.user;

import akka.actor.ActorRef;

public class Session {
  public final String id;
  public final ActorRef actor;
  public final Browser browser;

  public Session(
      final String id,
      final ActorRef actor,
      final Browser browser) {
    this.id = id;
    this.actor = actor;
    this.browser = browser;
  }

  @Override
  public String toString() {
    return String.format("Session(%s)", id);
  }
}
