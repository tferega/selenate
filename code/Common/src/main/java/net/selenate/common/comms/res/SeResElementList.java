package net.selenate.common.comms.res;

import java.io.Serializable;
import java.util.List;

public class SeResElementList implements Serializable {
  private static final long serialVersionUID = 1L;

  public final List<SeResElement>  elementList;

  public SeResElementList(final List<SeResElement>  elementList) {
    this.elementList = elementList;
  }

  public String toFullString(int indent) {
    String fullString = toString();
    for (final SeResElement element : elementList) {
      fullString += "\n" + element.toFullString(1);
    }

    return fullString;
  }

  @Override
  public String toString() {
    return String.format("SeResElements [%d elements]", elementList.size());
  }
}
