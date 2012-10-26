package net.selenate.server.actions;

import net.selenate.server.comms.*;

public interface IAction {
  public SeCapture capture();
  public Void click(final String xpath);
  public Void close();
  public Void get(final String url);
}
