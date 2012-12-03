package net.selenate.common.comms.res;

import java.io.Serializable;

public class SeResExecuteScript implements Serializable {
  private static final long serialVersionUID = 1L;

  public final String result;

  public SeResExecuteScript(final String result) {
    this.result = result;
  }
}
