package net.selenate.common.comms.res;

import java.io.Serializable;

import net.selenate.common.comms.SeElement;

public class SeResFindElement implements Serializable {
  private static final long serialVersionUID = 1L;

  public final SeElement element;

  public SeResFindElement(final SeElement element) {
    this.element = element;
  }

  public String toFullString() {
    return toFullString(0);
  }

  public String toFullString(int indent) {
    String fullString = pad(toString(), " ", indent*2);

    for (final SeElement child : element.children) {
      fullString += "\n" + child.toFullString(indent + 1);
    }

    return fullString;
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

    return String.format("SeResFindElement [%s]: %s%s", element.uuid, element.name, altDescStr);
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

  private static String pad(final String s, final String padding, final int indent) {
    String result = "";
    for (int n = 0; n < indent; n++) {
      result += padding;
    }
    result += s;
    return result;
  }
}
