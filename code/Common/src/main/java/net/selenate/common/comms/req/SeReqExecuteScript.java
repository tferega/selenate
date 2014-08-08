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
    final int length = javascript.length();
    if(length > 100)
      return String.format("SeReqExecuteScript(%s)", javascript.substring(length - 100, length - 1));
    else
      return String.format("SeReqExecuteScript(%s)", javascript);
  }
}
