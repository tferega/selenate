package net.selenate.common.comms.res;

import java.util.List;
import net.selenate.common.comms.SeOption;
import net.selenate.common.exceptions.SeNullArgumentException;

public final class SeResSelectFindOptionList implements SeCommsRes {
  private static final long serialVersionUID = 45749879L;

  private final List<SeOption> optionList;

  public SeResSelectFindOptionList(final List<SeOption> optionList) {
    this.optionList = optionList;
    validate();
  }

  public List<SeOption> getOptionList() {
    return optionList;
  }

  public SeResSelectFindOptionList withOptionList(final List<SeOption> newOptionList) {
    return new SeResSelectFindOptionList(newOptionList);
  }

  private void validate() {
    if (optionList == null) {
      throw new SeNullArgumentException("Option list");
    }
  }

  @Override
  public String toString() {
    return String.format("SeResSelectFindOptionList(%s)", optionList);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((optionList == null) ? 0 : optionList.hashCode());
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
    SeResSelectFindOptionList other = (SeResSelectFindOptionList) obj;
    if (optionList == null) {
      if (other.optionList != null)
        return false;
    } else if (!optionList.equals(other.optionList))
      return false;
    return true;
  }
}
