package net.selenate.common.sessions;

import java.io.Serializable;
import net.selenate.common.exceptions.SeNullArgumentException;

public class SessionRequest implements Serializable {
  private static final long serialVersionUID = 45749879L;

  private final String sessionID;
  private final boolean isRecorded;

  public SessionRequest(
      final String sessionID,
      final Boolean isRecorded) {
    this.sessionID = sessionID;
    this.isRecorded = isRecorded;
    validate();
  }

  public String getSessionID() {
    return sessionID;
  }

  public Boolean getIsRecorded() {
    return isRecorded;
  }

  public SessionRequest withSessionID(final String newSessionID) {
    return new SessionRequest(newSessionID, this.isRecorded);
  }

  public SessionRequest withIsRecorded(final boolean newIsRecorded) {
    return new SessionRequest(this.sessionID, newIsRecorded);
  }

  private void validate() {
    if (sessionID == null) {
      throw new SeNullArgumentException("Session ID");
    }
  }

  @Override
  public String toString() {
    return String.format("SessionRequest(%s, %s)",
        sessionID, String.valueOf(isRecorded));
  }
}
