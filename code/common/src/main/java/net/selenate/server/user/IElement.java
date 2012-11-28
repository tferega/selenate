package net.selenate.server.user;

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
  public Map<String, String> getAttributeList();
  public List<IElement>      getChildren();

  public ISelect toSelect();

  public void clearText();
  public void appendText();
  public void setText();
  public void click();
}
