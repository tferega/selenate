package net.selenate.common.comms;

import java.util.ArrayList;
import java.util.List;
import net.selenate.common.SelenateUtils;

public final class SePage implements SeComms {
  private static final long serialVersionUID = 45749879L;

  private final String                  name;
  private final List<SeElementSelector> selectorList;

  private static List<SeElementSelector> selectorToList(final SeElementSelector selector) {
    final List<SeElementSelector> selectorList = new ArrayList<SeElementSelector>();
    selectorList.add(selector);
    return selectorList;
  }

  public SePage(
      final String name,
      final SeElementSelector selector) {
    this(name, selectorToList(selector));
  }

  public SePage(
      final String                  name,
      final List<SeElementSelector> selectorList) {
    this.name         = name;
    this.selectorList = selectorList;
    validate();
  }

  public String getName() {
    return name;
  }

  public List<SeElementSelector> getSelectorList() {
    return selectorList;
  }

  public SePage withName(final String newName) {
    return new SePage(newName, this.selectorList);
  }

  public SePage withElementSelector(final List<SeElementSelector> newSelectorList) {
    return new SePage(this.name, newSelectorList);
  }

  private void validate() {
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null!");
    }

    if (selectorList == null) {
      throw new IllegalArgumentException("Selector list cannot be null!");
    }

    if (selectorList.isEmpty()) {
      throw new IllegalArgumentException("Selector list cannot be empty!");
    }
  }

  @Override
  public String toString() {
    return String.format("SePage(%s, %s)",
        name, SelenateUtils.listToString(selectorList));
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result
        + ((selectorList == null) ? 0 : selectorList.hashCode());
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
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (selectorList == null) {
      if (other.selectorList != null)
        return false;
    } else if (!selectorList.equals(other.selectorList))
      return false;
    return true;
  }
}
