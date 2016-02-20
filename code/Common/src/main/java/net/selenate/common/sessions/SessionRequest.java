package net.selenate.common.sessions;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import net.selenate.common.exceptions.SeNullArgumentException;

public class SessionRequest implements Serializable {
  private static final long serialVersionUID = 45749879L;

  private final String sessionID;
  private final Map<String, String> prefs;

  public SessionRequest(final String sessionID) {
    this(sessionID, Collections.emptyMap());
  }

  public SessionRequest(
      final String sessionID,
      final Map<String, String> prefs) {
    this.sessionID = sessionID;
    this.prefs = prefs;
    validate();
  }

  public String getSessionID() {
    return sessionID;
  }

  public Map<String, String> getPrefs() {
    return prefs;
  }

  public SessionRequest withSessionID(final String newSessionID) {
    return new SessionRequest(newSessionID, this.prefs);
  }

  public SessionRequest withPrefs(final Map<String, String> newPrefs) {
    return new SessionRequest(this.sessionID, newPrefs);
  }

  private void validate() {
    if (sessionID == null) {
      throw new SeNullArgumentException("Session ID");
    }
    if (prefs == null) {
      throw new SeNullArgumentException("Prefs");
    }
  }

  @Override
  public String toString() {
    return String.format("SessionRequest(%s, %s)",
        sessionID, prefs);
  }
}
