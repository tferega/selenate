package net.selenate.common.comms.req;

public class SeReqCapture implements SeCommsReq {
  private static final long serialVersionUID = 1L;

  public final String name;
  public final boolean takeScreenshot;

  public SeReqCapture(final String name) {
    if (name == null) {
      throw new IllegalArgumentException("name cannot be null!");
    }

    this.name           = name;
    this.takeScreenshot = true;
  }

  public SeReqCapture(final String name, final boolean takeScreenshot) {
    if (name == null) {
      throw new IllegalArgumentException("name cannot be null!");
    }

    this.name           = name;
    this.takeScreenshot = takeScreenshot;
  }


  @Override
  public String toString() {
    return String.format("SeReqCapture(%s, %s)", name, takeScreenshot);
  }
}
