package net.selenate.common.comms.res;

public class SeResElementExists extends SeCommsRes {
  private static final long serialVersionUID = 1L;

  public final boolean isFound;

  public SeResElementExists(final boolean isFound) {
    this.isFound = isFound;
  }
}
