package net.selenate.common.user;

import java.io.IOException;
import java.util.List;

import net.selenate.common.comms.req.SeCommsReq;

public interface IBrowser {
  public void open(String url) throws IOException;
  public Capture capture(String name) throws IOException;
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

  public String findAlert() throws IOException;

  public INavigation navigate() throws IOException;

  public void startKeepaliveClick(long delayMillis, ElementSelectMethod method, String query) throws IOException;
  public void startKeepaliveClick(long delayMillis, ElementSelector selector) throws IOException;

  public void startKeepalive(long delayMillis, SeCommsReq... reqList) throws IOException;
  public void startKeepalive(long delayMillis, List<SeCommsReq> reqList) throws IOException;

  public void stopKeepalive() throws IOException;
}
