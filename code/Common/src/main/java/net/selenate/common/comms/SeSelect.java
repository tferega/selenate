package net.selenate.common.comms;

import java.util.List;

public class SeSelect extends SeComms {
  private static final long serialVersionUID = 1L;

  public final SeElement element;
  public final int       optionCount;
  public final Integer   selectedIndex;
  public final SeOption  selectedOption;
  public final List<SeOption> options;

  public SeSelect(
      final SeElement element,
      final int       optionCount,
      final Integer   selectedIndex,
      final SeOption  selectedOption,
      final List<SeOption> options) {
    if (options == null) throw new IllegalArgumentException("Options cannot be null!");
    this.element        = element;
    this.optionCount    = optionCount;
    this.selectedIndex  = selectedIndex;
    this.selectedOption = selectedOption;
    this.options        = options;
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

    return String.format("SeSelect [%s]: %s%s", element.uuid, element.name, altDescStr);
  }
}
