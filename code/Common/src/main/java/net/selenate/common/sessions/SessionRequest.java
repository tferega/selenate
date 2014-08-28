package net.selenate.common.sessions;

import java.io.Serializable;

public class SessionRequest implements Serializable {
  private static final long serialVersionUID = 1L;

  private final String sessionID;
  private final Boolean isRecorded;

  public SessionRequest(
      final String sessionID,
      final Boolean isRecorded) {
    if (sessionID == null) {
      throw new IllegalArgumentException("Session ID cannot be null!");
    }

    if (isRecorded == null) {
      throw new IllegalArgumentException("Is recorded cannot be null!");
    }

    this.sessionID = sessionID;
    this.isRecorded = isRecorded;
  }

  public String getSessionID() {
    return sessionID;
  }

  public Boolean getIsRecorded() {
    return isRecorded;
  }

  @Override
  public String toString() {
    return String.format("SessionRequest(%s, %s)", sessionID, String.valueOf(isRecorded));
  }
}
