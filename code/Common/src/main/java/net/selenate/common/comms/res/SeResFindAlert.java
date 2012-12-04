package net.selenate.common.comms.res;

import java.io.Serializable;

public class SeResFindAlert implements Serializable {
  private static final long serialVersionUID = 1L;

  public final String text;

  public SeResFindAlert(final String text) {
    this.text = text;
  }
}
