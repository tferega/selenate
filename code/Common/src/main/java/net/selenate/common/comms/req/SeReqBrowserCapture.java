package net.selenate.common.comms.req;

import net.selenate.common.SelenateUtils;

public final class SeReqBrowserCapture implements SeCommsReq {
  private static final long serialVersionUID = 45749879L;

  private final String  name;
  private final boolean takeScreenshot;

  public SeReqBrowserCapture(
      final String  name,
      final boolean takeScreenshot) {
    this.name           = SelenateUtils.guardNull(name, "Name");
    this.takeScreenshot = takeScreenshot;
  }

  public String getName() {
    return name;
  }

  public boolean isTakeScreenshot() {
    return takeScreenshot;
  }

  public SeReqBrowserCapture withName(final String newName) {
    return new SeReqBrowserCapture(newName, this.takeScreenshot);
  }

  public SeReqBrowserCapture withTakeScreenshot(final boolean newTakeScreenshot) {
    return new SeReqBrowserCapture(this.name, newTakeScreenshot);
  }

  @Override
  public String toString() {
    return String.format("SeReqBrowserCapture(%s, %s)", name, takeScreenshot);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + (takeScreenshot ? 1231 : 1237);
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    SeReqBrowserCapture other = (SeReqBrowserCapture) obj;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (takeScreenshot != other.takeScreenshot)
      return false;
    return true;
  }
}