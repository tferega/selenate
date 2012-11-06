package net.selenate.server.actions;

import net.selenate.server.comms.req.SeReqCapture;

public interface ISession {
  public String getSessionID();

  public SeReqCapture capture();
  public boolean click(final String xpath);
  public boolean close();
  public boolean get(final String url);
  public boolean open();
}
