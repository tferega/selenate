package net.selenate.common.sessions;

import akka.actor.ActorRef;

import net.selenate.common.user.Options;

import scala.concurrent.Future;

public interface ISessionFactory {
  public Future<ActorRef> getSession(final String sessionID);
  public Future<ActorRef> getSession(final String sessionID, final Options options);
}
