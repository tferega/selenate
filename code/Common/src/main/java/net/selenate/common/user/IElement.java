package net.selenate.common.user;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IElement {
  public String   getUuid();
  public Position getPos();
  public Location getLoc();
  public String   getName();
  public String   getText();
  public boolean  getIsDisplayed();
  public boolean  getIsEnabled();
  public boolean  getIsSelected();
  public List<Integer>       getFramePath();
  public Map<String, String> getAttributeList();
  public List<IElement>      getChildren();

  public ISelect toSelect() throws IOException;

  public void clearText() throws IOException;
  public void appendText(String text) throws IOException;
  public void setText(String text) throws IOException;
  public void click() throws IOException;
}
