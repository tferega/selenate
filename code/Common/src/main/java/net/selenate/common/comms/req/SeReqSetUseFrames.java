package net.selenate.common.comms.req;

public class SeReqSetUseFrames implements SeCommsReq {
  private static final long serialVersionUID = 1L;

  public final Boolean useFrames;

  public SeReqSetUseFrames(final Boolean useFrames) {
    if (useFrames == null) {
      throw new IllegalArgumentException("UseFrames cannot be null!");
    }

    this.useFrames = useFrames;
  }

  @Override
  public String toString() {
    return String.format("SeReqSetUseFrames(%s)", useFrames);
  }
}
