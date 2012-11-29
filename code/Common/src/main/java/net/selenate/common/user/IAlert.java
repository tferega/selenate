package net.selenate.common.user;

import java.io.IOException;

public interface IAlert {
  public void accept() throws IOException;
  public void dismiss() throws IOException;
  public void getText() throws IOException;
  public void setText() throws IOException;
}
