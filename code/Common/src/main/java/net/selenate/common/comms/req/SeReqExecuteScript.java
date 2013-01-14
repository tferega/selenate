package net.selenate.common.comms.req;

public class SeReqExecuteScript implements SeCommsReq {
  private static final long serialVersionUID = 1L;

  public final String javascript;

  public SeReqExecuteScript(final String javascript) {
    if (javascript == null) {
      throw new IllegalArgumentException("Javascript cannot be null!");
    }

    this.javascript = javascript;
  }

  @Override
  public String toString() {
    return String.format("SeReqExecuteScript(%s)", javascript);
  }
}
