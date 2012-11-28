package net.selenate.server.user;

import java.util.List;

public interface ISelect extends IElement {
  public ISelect select(OptionSelector method, String query);

  public int getOptionCount();
  public int getSelectedIndex();

  public List<IOption> getOptions();
}
