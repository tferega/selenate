package net.selenate.common.user;

import java.io.IOException;
import java.util.List;

public interface ISelect extends IElement {
  public ISelect select(OptionSelectMethod method, String query) throws IOException;

  public int getOptionCount();
  public Integer getSelectedIndex();
  public IOption getSelectedOption();

  public List<IOption> getOptions();
}
