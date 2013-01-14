package net.selenate.common.comms.res;

import java.util.List;

import net.selenate.common.comms.SeElement;
import net.selenate.common.util.Util;

public class SeResFindElementList implements SeCommsRes {
  private static final long serialVersionUID = 1L;

  public final List<SeElement>  elementList;

  public SeResFindElementList(final List<SeElement>  elementList) {
    this.elementList = elementList;
  }

  @Override
  public String toString() {
    return String.format("SeResFindElementList()%s", Util.multilineListToString(elementList));
  }
}
