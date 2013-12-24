package net.selenate.common.comms.req;

import java.util.ArrayList;
import java.util.List;

import net.selenate.common.comms.*;
import net.selenate.common.util.Util;

public class SeReqWaitForBrowserPage implements SeCommsReq {
  private static final long serialVersionUID = 1L;

  public final List<SePage> pageList;

  public SeReqWaitForBrowserPage(final List<SePage> pageList) {
    if (pageList == null) {
      throw new IllegalArgumentException("Page list cannot be null!");
    }
    if (pageList.isEmpty()) {
      throw new IllegalArgumentException("Page list cannot be empty!");
    }

    this.pageList = pageList;
  }

  public SeReqWaitForBrowserPage(final SePage page) {
    if (page == null) {
      throw new IllegalArgumentException("Page cannot be null!");
    }

    this.pageList = new ArrayList<SePage>();
    this.pageList.add(page);
  }

  @Override
  public String toString() {
    return String.format("SeReqWaitForBrowserPage(%s)", Util.multilineListToString(pageList));
  }
}
