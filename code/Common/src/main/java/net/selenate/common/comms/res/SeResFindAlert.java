package net.selenate.common.comms.res;

public class SeResFindAlert implements SeCommsRes {
  private static final long serialVersionUID = 1L;

  public final String text;

  public SeResFindAlert(final String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return String.format("SeResFindAlert(%s)", text);
  }
}
