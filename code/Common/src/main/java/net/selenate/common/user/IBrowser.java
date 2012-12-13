package net.selenate.common.user;

import java.io.IOException;
import java.util.List;

public interface IBrowser {
  public void open(String url) throws IOException;
  public void capture(String name) throws IOException;
  public String executeScript(String javascript) throws IOException;
  public void resetFrame() throws IOException;
  public void switchFrame(int frame) throws IOException;

  public void quit() throws IOException;

  public IElement findElement(ElementSelectMethod method, String query) throws IOException;
  public List<IElement> findElementList(ElementSelectMethod method, String query) throws IOException;

  public boolean isAlert() throws IOException;
  public IAlert findAlert() throws IOException;
}
