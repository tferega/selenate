package net.selenate.common.sessions;

import net.selenate.common.user.Preferences;

public interface ISessionFactory {
  public String getSession(final String sessionID, final Preferences Preferences);
  public String getSession(final String sessionID);
}
