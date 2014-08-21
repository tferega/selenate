package net.selenate.common.sessions;

import java.io.Serializable;

public class SessionRequest implements Serializable {
  private static final long serialVersionUID = 1L;

  private final String sessionID;

  public SessionRequest(final String sessionID) {
    if (sessionID == null) {
      throw new IllegalArgumentException("Session ID cannot be null!");
    }

    this.sessionID = sessionID;
  }

  public String getSessionID() {
    return sessionID;
  }
}
