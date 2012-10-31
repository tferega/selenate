package net.selenate.server.actions;

import net.selenate.server.comms.*;

public interface ISession {
  public String getSessionID();

  public SeCapture capture();
  public boolean click(final String xpath);
  public boolean close();
  public boolean get(final String url);
  public boolean open();
}
