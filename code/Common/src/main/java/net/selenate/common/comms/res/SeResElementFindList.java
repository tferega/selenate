package net.selenate.common.comms.res;

import java.util.List;
import net.selenate.common.comms.SeElement;
import net.selenate.common.exceptions.SeNullArgumentException;
import net.selenate.common.SelenateUtils;

public final class SeResElementFindList implements SeCommsRes {
  private static final long serialVersionUID = 45749879L;

  private final List<SeElement> elementList;

  public SeResElementFindList(final List<SeElement> elementList) {
   this.elementList = elementList;
   validate();
  }

  public List<SeElement> getElementList() {
    return elementList;
  }

  public SeResElementFindList withElementList(final List<SeElement> newElementList) {
    return new SeResElementFindList(newElementList);
  }

  private void validate() {
    if (elementList == null) {
      throw new SeNullArgumentException("Element list");
    }
  }

  @Override
  public String toString() {
    return String.format("SeResElementFindList(%s)",
        SelenateUtils.listToString(elementList));
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result
        + ((elementList == null) ? 0 : elementList.hashCode());
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
    SeResElementFindList other = (SeResElementFindList) obj;
    if (elementList == null) {
      if (other.elementList != null)
        return false;
    } else if (!elementList.equals(other.elementList))
      return false;
    return true;
  }
}
