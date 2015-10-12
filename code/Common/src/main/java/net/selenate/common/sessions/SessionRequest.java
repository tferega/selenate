package net.selenate.common.sessions;

import java.io.Serializable;

import net.selenate.common.SelenateUtils;

public class SessionRequest implements Serializable {
  private static final long serialVersionUID = 45749879L;

  private final String  sessionID;
  private final String  poolName; // can be null
  private final boolean isRecorded;

  public SessionRequest(final String sessionID, final String poolName, final boolean isRecorded) {
    this.sessionID = SelenateUtils.guardNullOrEmpty(sessionID, "SessionID");
    this.poolName  = poolName;
    this.isRecorded = isRecorded;
  }

  public String getSessionID() {
    return sessionID;
  }

  public Boolean getIsRecorded() {
    return isRecorded;
  }

  public String getPoolName() {
    return poolName;
  }

  @Override
  public String toString() {
    return String.format("SessionRequest(%s, %s, %s)", sessionID, poolName, String.valueOf(isRecorded));
  }
}
