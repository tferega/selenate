package net.selenate.common.user;

import java.io.IOException;
import java.util.List;

public interface IBrowser {
  public void open(String url) throws IOException;
  public void capture(String name) throws IOException;
  public String executeScript(String javascript) throws IOException;

  public void quit() throws IOException;

  public IElement findElement(ElementSelector method, String query) throws IOException;

  public boolean isAlert() throws IOException;
  public IAlert getAlert() throws IOException;
}
