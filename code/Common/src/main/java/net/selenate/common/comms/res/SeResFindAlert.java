package net.selenate.common.comms.res;

public class SeResFindAlert implements SeCommsRes {
  private static final long serialVersionUID = 1L;

  public final String text;

  public SeResFindAlert(final String text) {
    if (text == null) {
      throw new IllegalArgumentException("Text cannot be null!");
    }

    this.text = text;
  }

  @Override
  public String toString() {
    return String.format("SeResFindAlert(%s)", text);
  }
}
