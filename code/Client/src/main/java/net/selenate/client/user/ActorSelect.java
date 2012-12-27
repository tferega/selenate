package net.selenate.client.user;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import akka.actor.ActorRef;

import net.selenate.common.user.*;

public class ActorSelect extends ActorElement implements ISelect {

  public final int       optionCount;
  public final Integer   selectedIndex;
  public final IOption   selectedOption;
  public final List<IOption> options;

  public ActorSelect(
      final ActorRef session,
      final String   uuid,
      final Position pos,
      final Location loc,
      final String   name,
      final String   text,
      final boolean  isDisplayed,
      final boolean  isEnabled,
      final boolean  isSelected,
      final int      optionCount,
      final Integer  selectedIndex,
      final IOption selectedOption,
      final List<IOption>       options,
      final List<Integer>       framePath,
      final Map<String, String> attributeList,
      final List<IElement>      children) {
    super(session, uuid, pos, loc, name, text, isDisplayed, isEnabled, isSelected, framePath, attributeList, children);
    this.optionCount    = optionCount;
    this.selectedIndex  = selectedIndex;
    this.selectedOption = selectedOption;
    this.options        = options;
  }

  public ActorSelect(
      final ActorRef session,
      final IElement element,
      final int      optionCount,
      final Integer  selectedIndex,
      final IOption selectedOption,
      final List<IOption> options) {
    super(session, element.getUuid(), element.getPos(), element.getLoc(), element.getName(), element.getText(), element.getIsDisplayed(), element.getIsEnabled(), element.getIsSelected(), element.getFramePath(), element.getAttributeList(), element.getChildren());
    this.optionCount    = optionCount;
    this.selectedIndex  = selectedIndex;
    this.selectedOption = selectedOption;
    this.options        = options;
  }

  @Override
  public ISelect select(OptionSelectMethod method, String query)
      throws IOException {
    throw new IllegalArgumentException("Not supported!");
  }

  @Override
  public int getOptionCount() {
    return optionCount;
  }

  @Override
  public Integer getSelectedIndex() {
    return selectedIndex;
  }

  @Override
  public IOption getSelectedOption() {
    return selectedOption;
  }

  @Override
  public List<IOption> getOptions() {
    return options;
  }
}
