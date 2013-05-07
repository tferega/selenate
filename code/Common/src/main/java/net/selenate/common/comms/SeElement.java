package net.selenate.common.comms;

import java.util.List;
import java.util.Map;

public class SeElement implements SeComms {
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
  public final String  windowHandle;
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
      final String  windowHandle,
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
    this.windowHandle  = windowHandle;
    this.framePath     = framePath;
    this.attributeList = attributeList;
  }

  @Override
  public String toString() {
    return String.format("SeElement(%s, %s)", name, getDesc());
  }

  public String getDesc() {
    return orElse(attributeList.get("id"), attributeList.get("name"), attributeList.get("class"));
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
