package net.selenate.common.comms.res;

public class SeResExecuteScript extends SeCommsRes {
  private static final long serialVersionUID = 1L;

  public final String result;

  public SeResExecuteScript(final String result) {
    this.result = result;
  }
}
