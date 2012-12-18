package net.selenate.common.comms.res;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class SeResElement implements Serializable {
  private static final long serialVersionUID = 1L;

  public final String  uuid;
  public final int     posX;
  public final int     posY;
  public final int     width;
  public final int     height;
  public final String  name;
  public final String  text;
  public final boolean isDisplayed;
  public final boolean isEnabled;
  public final boolean isSelected;
  public final List<Integer>       framePath;
  public final Map<String, String> attributeList;
  public final List<SeResElement>  children;

  public SeResElement(
      final String  uuid,
      final int     posX,
      final int     posY,
      final int     width,
      final int     height,
      final String  name,
      final String  text,
      final boolean isDisplayed,
      final boolean isEnabled,
      final boolean isSelected,
      final List<Integer>       framePath,
      final Map<String, String> attributeList,
      final List<SeResElement>  children) {
    this.uuid          = uuid;
    this.posX          = posX;
    this.posY          = posY;
    this.width         = width;
    this.height        = height;
    this.name          = name;
    this.text          = text;
    this.isDisplayed   = isDisplayed;
    this.isEnabled     = isEnabled;
    this.isSelected    = isSelected;
    this.framePath     = framePath;
    this.attributeList = attributeList;
    this.children      = children;
  }

  public String toFullString() {
    return toFullString(0);
  }

  public String toFullString(int indent) {
    String fullString = pad(toString(), " ", indent*2);

    for (final SeResElement child : children) {
      fullString += "\n" + child.toFullString(indent + 1);
    }

    return fullString;
  }


  @Override
  public String toString() {
    final String altDesc = orElse(attributeList.get("id"), attributeList.get("name"), attributeList.get("class"));
    final String altDescStr;
    if (altDesc != null) {
      altDescStr = String.format(" (%s)", altDesc);
    }
    else {
      altDescStr = "";
    }

    return String.format("SeResElement [%s]: %s%s", uuid, name, altDescStr);
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
