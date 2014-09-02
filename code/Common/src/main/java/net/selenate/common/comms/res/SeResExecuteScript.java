package net.selenate.common.comms.res;

public class SeResExecuteScript implements SeCommsRes {
  private static final long serialVersionUID = 1L;

  public final String result;

  public SeResExecuteScript(final String result) {
    if (result == null) {
      throw new IllegalArgumentException("Result cannot be null!");
    }
    this.result = result;
  }

  @Override
  public String toString() {
    return String.format("SeResExecuteScript(%s)", result);
  }
}
