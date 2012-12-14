package net.selenate.common.user;

import java.util.List;

public class BrowserPage {
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
}
