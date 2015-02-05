package net.selenate.common.comms.req;

public class SeReqCapture implements SeCommsReq {
  private static final long serialVersionUID = 1L;

  public final String  name;
  public final boolean takeScreenshot;
  public final int     screenshotWindowIndex;

  public SeReqCapture(final String name) {
    this(name, true);
  }

  public SeReqCapture(final String name, final boolean takeScreenshot) {
    this(name, takeScreenshot, 0);
  }

  public SeReqCapture(final String name, final boolean takeScreenshot, final int screenShotWindowIndex) {
    if (name == null) {
      throw new IllegalArgumentException("name cannot be null!");
    }

    this.name                  = name;
    this.takeScreenshot        = takeScreenshot;
    this.screenshotWindowIndex = screenShotWindowIndex;
  }

  @Override
  public String toString() {
    return String.format("SeReqCapture(%s, %s)", name, takeScreenshot);
  }
}
