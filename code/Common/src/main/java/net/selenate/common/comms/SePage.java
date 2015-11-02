package net.selenate.common.comms;

import java.util.ArrayList;
import java.util.List;
import net.selenate.common.SelenateUtils;
import net.selenate.common.exceptions.SeNullArgumentException;

public final class SePage implements SeComms {
  private static final long serialVersionUID = 45749879L;

  private final String                  name;
  private final List<SeElementSelector> presentSelectorList;
  private final List<SeElementSelector> absentSelectorList;

  private static List<SeElementSelector> selectorToList(final SeElementSelector selector) {
    final List<SeElementSelector> selectorList = new ArrayList<SeElementSelector>();
    selectorList.add(selector);
    return selectorList;
  }

  public SePage(
      final String name,
      final SeElementSelector presentSelector) {
    this(name, selectorToList(presentSelector), new ArrayList<>());
  }

  public SePage(
      final String name,
      final SeElementSelector presentSelector,
      final SeElementSelector absentSelector) {
    this(name, selectorToList(presentSelector), selectorToList(absentSelector));
  }

  public SePage(
      final String                  name,
      final List<SeElementSelector> presentSelectorList) {
    this(name, presentSelectorList, new ArrayList<>());
  }

  public SePage(
      final String                  name,
      final List<SeElementSelector> presentSelectorList,
      final List<SeElementSelector> absentSelectorList) {
    this.name                = name;
    this.presentSelectorList = presentSelectorList;
    this.absentSelectorList  = absentSelectorList;
    validate();
  }

  public String getName() {
    return name;
  }

  public List<SeElementSelector> getPresentSelectorList() {
    return presentSelectorList;
  }

  public List<SeElementSelector> getAbsentSelectorList() {
    return absentSelectorList;
  }

  public SePage withName(final String newName) {
    return new SePage(newName, this.presentSelectorList, this.absentSelectorList);
  }

  public SePage withPresentSelectorList(final List<SeElementSelector> newPresentSelectorList) {
    return new SePage(this.name, newPresentSelectorList, this.absentSelectorList);
  }

  public SePage withAbsentSelectorList(final List<SeElementSelector> newAbsentSelectorList) {
    return new SePage(this.name, this.presentSelectorList, newAbsentSelectorList);
  }

  private void validate() {
    if (name == null) {
      throw new SeNullArgumentException("Name");
    }

    if (presentSelectorList == null) {
      throw new SeNullArgumentException("Present selector list");
    }

    if (absentSelectorList == null) {
      throw new SeNullArgumentException("Absent selector list");
    }
  }

  @Override
  public String toString() {
    return String.format("SePage(%s, %s, %s)",
        name,
        SelenateUtils.listToString(presentSelectorList),
        SelenateUtils.listToString(absentSelectorList));
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result
        + ((absentSelectorList == null) ? 0 : absentSelectorList.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result
        + ((presentSelectorList == null) ? 0 : presentSelectorList.hashCode());
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
    SePage other = (SePage) obj;
    if (absentSelectorList == null) {
      if (other.absentSelectorList != null)
        return false;
    } else if (!absentSelectorList.equals(other.absentSelectorList))
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (presentSelectorList == null) {
      if (other.presentSelectorList != null)
        return false;
    } else if (!presentSelectorList.equals(other.presentSelectorList))
      return false;
    return true;
  }
}
