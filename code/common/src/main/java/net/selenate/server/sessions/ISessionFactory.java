package net.selenate.server.sessions;

public interface ISessionFactory {
  public String getSession(final String sessionID);
}
