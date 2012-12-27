package net.selenate.common.comms.res;

import java.io.Serializable;

import net.selenate.common.comms.*;

public class SeResFindSelect implements Serializable {
  private static final long serialVersionUID = 1L;

  public final SeSelect select;

  public SeResFindSelect(final SeSelect select) {
    this.select = select;
  }

  @Override
  public String toString() {
    final String altDesc = orElse(select.element.attributeList.get("id"), select.element.attributeList.get("name"), select.element.attributeList.get("class"));
    final String altDescStr;
    if (altDesc != null) {
      altDescStr = String.format(" (%s)", altDesc);
    }
    else {
      altDescStr = "";
    }

    return String.format("SeResFindSelect [%s]: %s%s", select.element.uuid, select.element.name, altDescStr);
  }

  private static String orElse(final String ... args) {
    String result = null;
    for (final String entry : args) {
      if (entry != null) {
        result = entry;
        break;
      }
    }

    return result;
  }
}
