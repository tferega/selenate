package net.selenate.server.user;

import java.util.List;

public interface IBrowser {
  public void open(String url);
  public List<IElement> getDom();
  public void capture(String name);
  public void executeScript(String javascript);

  public void quit();

  public IElement tryGetElement(ElementSelector method, String query);

  public boolean isAlert();
  public IAlert getAlert();
}
