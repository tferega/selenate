package net.selenate.common.comms.req;

public class SeReqSystemInput implements SeCommsReq {
  private static final long serialVersionUID = 1L;

  public final String input;

  public SeReqSystemInput(final String input) {
    if(input == null) {
      throw new IllegalArgumentException("Input cannot be null!");
    }

    this.input = input;
  }

  @Override
  public String toString() {
    return String.format("SeReqSystemInput(%s)", input);
  }
}
