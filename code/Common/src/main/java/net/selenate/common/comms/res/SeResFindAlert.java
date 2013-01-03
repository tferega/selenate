package net.selenate.common.comms.res;

public class SeResFindAlert extends SeCommsRes {
  private static final long serialVersionUID = 1L;

  public final String text;

  public SeResFindAlert(final String text) {
    this.text = text;
  }
}
