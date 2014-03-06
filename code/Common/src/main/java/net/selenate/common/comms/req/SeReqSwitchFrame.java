package net.selenate.common.comms.req;

import net.selenate.common.comms.SeElementSelectMethod;
import net.selenate.common.comms.SeElementSelector;

public class SeReqSwitchFrame implements SeCommsReq {
  private static final long serialVersionUID = 1L;

  public final Integer           frame;
  public final SeElementSelector selector;

  public SeReqSwitchFrame(final int frame) {
    this.frame   = frame;
    this.selector = null;
  }

  public SeReqSwitchFrame(final SeElementSelector selector) {
    if(selector == null) {
      throw new IllegalArgumentException("Selector cannot be null!");
    }

    this.frame   = null;
    this.selector = selector;
  }

  public SeReqSwitchFrame(final SeElementSelectMethod method, final String query) {
    this(new SeElementSelector(method, query));
  }

  @Override
  public String toString() {
    return String.format("SeReqSwitchFrame(%d, %s)", frame, selector.toString());
  }
}
