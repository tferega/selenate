package net.selenate.common.comms;

import java.util.List;
import java.util.Map;

public class SeElement extends SeComms {
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

  public SeElement(
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
      final Map<String, String> attributeList) {
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

    return String.format("SeElement [%s]: %s%s", uuid, name, altDescStr);
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
