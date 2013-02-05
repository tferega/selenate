package net.selenate.common.user;

import java.io.IOException;
import java.util.List;

public interface ISelect extends IElement {
  public ISelect selectByIndex(int id) throws IOException;
  public ISelect select(OptionSelectMethod method, String query) throws IOException;

  public int getOptionCount();
  public Integer getSelectedIndex();
  public IOption getSelectedOption();

  public List<IOption> getOptions();
}
