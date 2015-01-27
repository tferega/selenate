package net.selenate.common.comms;

public class SeS3Props {
  public final String realm;   // parent directory for all session stored sources
  public final String sessionID;

  public SeS3Props(String sessionID, String realm) {
    this.realm = realm;
    this.sessionID = sessionID;
  }
}
