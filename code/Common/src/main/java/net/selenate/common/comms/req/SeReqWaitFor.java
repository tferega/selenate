package net.selenate.common.comms.req;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SeReqWaitFor implements Serializable {
  private static final long serialVersionUID = 1L;

  public final List<SeReqPage> pageList;

  public SeReqWaitFor(final List<SeReqPage> pageList) {
    if (pageList == null) {
      throw new IllegalArgumentException("Page list cannot be null!");
    }
    if (pageList.isEmpty()) {
      throw new IllegalArgumentException("Page list cannot be empty!");
    }

    this.pageList = pageList;
  }

  public SeReqWaitFor(final SeReqPage page) {
    if (page == null) {
      throw new IllegalArgumentException("Page cannot be null!");
    }

    this.pageList = new ArrayList<SeReqPage>();
    this.pageList.add(page);
  }
}
