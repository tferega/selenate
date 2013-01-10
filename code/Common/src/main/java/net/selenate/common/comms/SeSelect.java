package net.selenate.common.comms;

import java.util.List;

public class SeSelect implements SeComms {
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
    return String.format("SeSelect(%s, %d options)", element.getDesc(), optionCount);
  }
}
