package net.selenate.common.sessions;

import akka.actor.ActorRef;

import net.selenate.common.user.Preferences;

import scala.concurrent.Future;

public interface ISessionFactory {
  public Future<ActorRef> getSession(final String sessionID, final Preferences Preferences);
  public Future<ActorRef> getSession(final String sessionID);
  public Future<ActorRef> getSession(final String sessionID, final Preferences Preferences, final Boolean useFrames);
  public Future<ActorRef> getSession(final String sessionID, final Boolean useFrames);
}
