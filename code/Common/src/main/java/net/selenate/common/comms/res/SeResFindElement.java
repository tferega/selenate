package net.selenate.common.comms.res;

import net.selenate.common.comms.SeElement;

public class SeResFindElement implements SeCommsRes {
  private static final long serialVersionUID = 1L;

  public final SeElement element;

  public SeResFindElement(final SeElement element) {
    if (element == null) {
      throw new IllegalArgumentException("Element cannot be null!");
    }

    this.element = element;
  }

  @Override
  public String toString() {
    return String.format("SeResFindElement(%s)", element);
  }
}
