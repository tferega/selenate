package net.selenate.common.comms.res;

import net.selenate.common.comms.SeElement;

public class SeResFindElement implements SeCommsRes {
  private static final long serialVersionUID = 1L;

  public final SeElement element;

  public SeResFindElement(final SeElement element) {
    this.element = element;
  }

  @Override
  public String toString() {
    return String.format("SeResFindElement(%s, %s)", element.name, element.getDesc());
  }
}
