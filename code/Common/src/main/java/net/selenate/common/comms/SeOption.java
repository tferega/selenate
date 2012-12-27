package net.selenate.common.comms;

import java.io.Serializable;

public class SeOption implements Serializable {
  private static final long serialVersionUID = 1L;

  public final SeElement element;

  public SeOption(final SeElement element) {
    this.element = element;
  }

  @Override
  public String toString() {
    final String altDesc = orElse(element.attributeList.get("id"), element.attributeList.get("name"), element.attributeList.get("class"));
    final String altDescStr;
    if (altDesc != null) {
      altDescStr = String.format(" (%s)", altDesc);
    }
    else {
      altDescStr = "";
    }

    return String.format("SeOption [%s]: %s%s", element.uuid, element.name, altDescStr);
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
