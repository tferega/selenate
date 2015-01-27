package net.selenate.common.comms.abs;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractSeWindow<T extends AbstractSeFrame<T>> implements Serializable{
  private static final long serialVersionUID = 1L;
  public final String html;
  public final byte[] screenshot;
  public final List<T> frameList;

  public AbstractSeWindow(
      final String html,
      final byte[] screenshot,
      final List<T> frameList) {
    this.html       = html;
    this.screenshot = screenshot;
    this.frameList  = frameList;
  }

}
