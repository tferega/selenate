package net.selenate.common.comms.res;

import java.util.List;
import net.selenate.common.comms.*;
import net.selenate.common.exceptions.SeNullArgumentException;

public final class SeResSelectFindList implements SeCommsRes {
  private static final long serialVersionUID = 45749879L;

  private final List<SeSelect> selectList;

  public SeResSelectFindList(final List<SeSelect> selectList) {
    this.selectList = selectList;
    validate();
  }

  public List<SeSelect> getSelect() {
    return selectList;
  }

  public SeResSelectFindList withSelectList(final List<SeSelect> newSelectList) {
    return new SeResSelectFindList(newSelectList);
  }

  private void validate() {
    if (selectList == null) {
      throw new SeNullArgumentException("Select list");
    }
  }

  @Override
  public String toString() {
    return String.format("SeResSelectFindList(%s)", selectList);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((selectList == null) ? 0 : selectList.hashCode());
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
    SeResSelectFindList other = (SeResSelectFindList) obj;
    if (selectList == null) {
      if (other.selectList != null)
        return false;
    } else if (!selectList.equals(other.selectList))
      return false;
    return true;
  }
}
