package net.selenate.server.sessions;

import java.util.Map;

public interface ISessionFactory {
  public String getSession(final String sessionID, final Map<String, String> profileJSON);
  public String getSession(final String sessionID);
}
