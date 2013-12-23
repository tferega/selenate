package net.selenate.common.user;

import java.io.Serializable;
import java.util.List;

public class BrowserPage implements Serializable{
  private static final long serialVersionUID = -4227576752950157494L;

  public String name;
  public List<ElementSelector> selectorList;

  public BrowserPage(
      final String name,
      final List<ElementSelector> selectorList) {
    if (name == null) {
      throw new IllegalArgumentException("Name list cannot be null!");
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
    BrowserPage other = (BrowserPage) obj;
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
