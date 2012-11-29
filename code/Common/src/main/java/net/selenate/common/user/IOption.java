package net.selenate.common.user;

import java.io.IOException;

public interface IOption extends IElement {
  public boolean isSelected();
  public void select() throws IOException;
}
