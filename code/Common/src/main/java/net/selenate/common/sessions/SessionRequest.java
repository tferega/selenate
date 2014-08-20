package net.selenate.common.sessions;

public class SessionRequest {
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
