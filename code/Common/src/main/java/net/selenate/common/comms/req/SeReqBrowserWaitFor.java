package net.selenate.common.comms.req;

import java.util.List;
import net.selenate.common.SelenateUtils;
import net.selenate.common.comms.SePage;

public final class SeReqBrowserWaitFor implements SeCommsReq {
  private static final long serialVersionUID = 45749879L;

  private final List<SePage> pageList;

  public SeReqBrowserWaitFor(final List<SePage> pageList) {
    this.pageList = pageList;
    validate();
  }

  public List<SePage> getPageList() {
    return pageList;
  }

  public SeReqBrowserWaitFor withPageList(final List<SePage> newPageList) {
    return new SeReqBrowserWaitFor(newPageList);
  }

  private void validate() {
    if (pageList == null) {
      throw new IllegalArgumentException("Page list cannot be null!");
    }

    if (pageList.isEmpty()) {
      throw new IllegalArgumentException("Page list cannot be empty!");
    }
  }

  @Override
  public String toString() {
    return String.format("SeReqBrowserWaitFor(%s)", SelenateUtils.listToString(pageList));
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((pageList == null) ? 0 : pageList.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    SeReqBrowserWaitFor other = (SeReqBrowserWaitFor) obj;
    if (pageList == null) {
      if (other.pageList != null)
        return false;
    } else if (!pageList.equals(other.pageList))
      return false;
    return true;
  }
}