package net.selenate.common.comms;

import java.util.List;
import net.selenate.common.SelenateUtils;

public final class SeSelect implements SeComms {
  private static final long serialVersionUID = 45749879L;

  private final SeElement      element;
  private final int            optionCount;
  private final int            selectedIndex;
  private final SeOption       selectedOption;
  private final List<SeOption> options;

  public SeSelect(
      final SeElement      element,
      final int            optionCount,
      final int            selectedIndex,
      final SeOption       selectedOption,
      final List<SeOption> options)
  {
    this.element        = SelenateUtils.guardNull(element, "Element");
    this.optionCount    = optionCount;
    this.selectedIndex  = selectedIndex;
    this.selectedOption = SelenateUtils.guardNull(selectedOption, "SelectedOption");
    this.options        = SelenateUtils.guardNull(options, "Options");
  }

  public SeElement getElement() {
    return element;
  }

  public int getOptionCount() {
    return optionCount;
  }

  public int getSelectedIndex() {
    return selectedIndex;
  }

  public SeOption getSelectedOption() {
    return selectedOption;
  }

  public List<SeOption> getOptions() {
    return options;
  }

  public SeSelect withElement(final SeElement newElement) {
    return new SeSelect(newElement, this.optionCount, this.selectedIndex, this.selectedOption, this.options);
  }

  public SeSelect withOptionCount(final int newOptionCount) {
    return new SeSelect(this.element, newOptionCount, this.selectedIndex, this.selectedOption, this.options);
  }

  public SeSelect withSelectedIndex(final Integer newSelectedIndex) {
    return new SeSelect(this.element, this.optionCount, newSelectedIndex, this.selectedOption, this.options);
  }

  public SeSelect withSelectedOption(final SeOption newSelectedOption) {
    return new SeSelect(this.element, this.optionCount, this.selectedIndex, newSelectedOption, this.options);
  }

  public SeSelect withOptions(final List<SeOption> newOptions) {
    return new SeSelect(this.element, this.optionCount, this.selectedIndex, this.selectedOption, newOptions);
  }

  @Override
  public String toString() {
    return String.format("SeSelect(%s, %d, %d, %s, %s)",
        element, optionCount, selectedIndex, selectedOption, SelenateUtils.listToString(options));
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((element == null) ? 0 : element.hashCode());
    result = prime * result + optionCount;
    result = prime * result + ((options == null) ? 0 : options.hashCode());
    result = prime * result + selectedIndex;
    result = prime * result
        + ((selectedOption == null) ? 0 : selectedOption.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    SeSelect other = (SeSelect) obj;
    if (element == null) {
      if (other.element != null)
        return false;
    } else if (!element.equals(other.element))
      return false;
    if (optionCount != other.optionCount)
      return false;
    if (options == null) {
      if (other.options != null)
        return false;
    } else if (!options.equals(other.options))
      return false;
    if (selectedIndex != other.selectedIndex)
      return false;
    if (selectedOption == null) {
      if (other.selectedOption != null)
        return false;
    } else if (!selectedOption.equals(other.selectedOption))
      return false;
    return true;
  }
}
