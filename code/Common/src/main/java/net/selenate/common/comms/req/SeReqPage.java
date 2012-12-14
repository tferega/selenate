package net.selenate.common.comms.req;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SeReqPage implements Serializable {
  private static final long serialVersionUID = 1L;

  public final String name;
  public final List<SeReqElementSelector> selectorList;

  public SeReqPage(
      final String name,
      final List<SeReqElementSelector> selectorList) {
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null!");
    }
    if (selectorList == null) {
      throw new IllegalArgumentException("Selector list cannot be null!");
    }
    if (selectorList.isEmpty()) {
      throw new IllegalArgumentException("Selector list cannot be empty!");
    }

    this.name         = name;
    this.selectorList = selectorList;
  }

  public SeReqPage(
      final String name,
      final SeReqElementSelector selector) {
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null!");
    }
    if (selector == null) {
      throw new IllegalArgumentException("Selector cannot be null!");
    }

    this.name         = name;
    this.selectorList = new ArrayList<SeReqElementSelector>();
    this.selectorList.add(selector);
  }
}
