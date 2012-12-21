package net.selenate.common.comms.res;

import java.io.Serializable;
import java.util.List;

import net.selenate.common.comms.SeElement;

public class SeResFindElementList implements Serializable {
  private static final long serialVersionUID = 1L;

  public final List<SeElement>  elementList;

  public SeResFindElementList(final List<SeElement>  elementList) {
    this.elementList = elementList;
  }

  public String toFullString(int indent) {
    String fullString = toString();
    for (final SeElement element : elementList) {
      fullString += "\n" + element.toFullString(1);
    }

    return fullString;
  }

  @Override
  public String toString() {
    return String.format("SeResElements [%d elements]", elementList.size());
  }
}
