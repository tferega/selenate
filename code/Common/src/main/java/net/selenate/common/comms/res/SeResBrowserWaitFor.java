package net.selenate.common.comms.res;

import net.selenate.common.comms.SePage;
import net.selenate.common.exceptions.SeInvalidArgumentException;

public final class SeResBrowserWaitFor implements SeCommsRes {
  private static final long serialVersionUID = 45749879L;

  private final boolean isSuccessful;
  private final SePage  foundPage;

  public SeResBrowserWaitFor(
      final boolean isSuccessful,
      final SePage  foundPage) {
    if (isSuccessful) {
      if (foundPage == null) throw new SeInvalidArgumentException("When successful, found page cannot be null!");
    } else {
      if (foundPage != null) throw new SeInvalidArgumentException("When unsuccessful, found page must be null!");
    }

    this.isSuccessful = isSuccessful;
    this.foundPage    = foundPage;
  }

  public boolean IsSuccessful() {
    return isSuccessful;
  }

  public SePage getFoundPage() {
    return foundPage;
  }

  public SeResBrowserWaitFor withIsSuccessful(final boolean newIsSuccessful) {
    return new SeResBrowserWaitFor(newIsSuccessful, this.foundPage);
  }

  public SeResBrowserWaitFor withFoundPage(final SePage newFoundPage) {
    return new SeResBrowserWaitFor(this.isSuccessful, newFoundPage);
  }

  @Override
  public String toString() {
    return String.format("SeResBrowserWaitFor(%b, %s)", isSuccessful, foundPage);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((foundPage == null) ? 0 : foundPage.hashCode());
    result = prime * result + (isSuccessful ? 1231 : 1237);
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
    SeResBrowserWaitFor other = (SeResBrowserWaitFor) obj;
    if (foundPage == null) {
      if (other.foundPage != null)
        return false;
    } else if (!foundPage.equals(other.foundPage))
      return false;
    if (isSuccessful != other.isSuccessful)
      return false;
    return true;
  }
}