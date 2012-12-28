package net.selenate.common.user;

import java.io.IOException;
import java.util.List;

public interface IBrowser {
  public void open(String url) throws IOException;
  public void capture(String name) throws IOException;
  public String executeScript(String javascript) throws IOException;
  public void resetFrame() throws IOException;
  public void switchFrame(int frame) throws IOException;

  public boolean waitFor(List<ElementSelector> selectorList) throws IOException;
  public boolean waitFor(ElementSelector... selectorList) throws IOException;
  public boolean waitFor(BrowserPage page) throws IOException;
  public String waitForAny(BrowserPage... pageList) throws IOException;
  public String waitForAny(List<BrowserPage> pageList) throws IOException;

  public void quit() throws IOException;

  public boolean elementExists(ElementSelectMethod method, String query) throws IOException;
  public boolean elementExists(ElementSelector selector) throws IOException;

  public IElement findElement(ElementSelectMethod method, String query) throws IOException;
  public IElement findElement(ElementSelector selector) throws IOException;

  public List<IElement> findElementList(ElementSelectMethod method, String query) throws IOException;
  public List<IElement> findElementList(ElementSelector selector) throws IOException;

  public boolean isAlert() throws IOException;
  public IAlert findAlert() throws IOException;
}
