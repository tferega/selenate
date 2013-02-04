package net.selenate.common.comms.res;

import net.selenate.common.comms.SeSelect;

public class SeResSelectOption implements SeCommsRes {
  private static final long serialVersionUID = 1L;

  public final SeSelect select;

  public SeResSelectOption(final SeSelect select) {
    this.select = select;
  }

  @Override
  public String toString() {
    return String.format("SeResSelectOption(%s)", select.element.getDesc());
  }
}
