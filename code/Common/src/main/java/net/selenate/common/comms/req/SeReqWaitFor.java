package net.selenate.common.comms.req;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SeReqWaitFor implements Serializable {
  private static final long serialVersionUID = 1L;

  public final List<SeReqElementSelector> selectorList;

  public SeReqWaitFor(final List<SeReqElementSelector> selectorList) {
    if (selectorList == null) {
      throw new IllegalArgumentException("Selector list cannot be null!");
    }
    if (selectorList.isEmpty()) {
      throw new IllegalArgumentException("Selector list cannot be empty!");
    }

    this.selectorList = selectorList;
  }

  public SeReqWaitFor(final SeReqElementSelector selector) {
    if (selector == null) {
      throw new IllegalArgumentException("Selector cannot be null!");
    }

    this.selectorList = new ArrayList<SeReqElementSelector>();
    this.selectorList.add(selector);
  }
}
