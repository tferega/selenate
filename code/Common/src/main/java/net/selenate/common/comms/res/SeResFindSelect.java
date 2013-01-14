package net.selenate.common.comms.res;

import net.selenate.common.comms.*;

public class SeResFindSelect implements SeCommsRes {
  private static final long serialVersionUID = 1L;

  public final SeSelect select;

  public SeResFindSelect(final SeSelect select) {
    this.select = select;
  }

  @Override
  public String toString() {
    return String.format("SeResFindSelect(%s)", select.element.getDesc());
  }
}
