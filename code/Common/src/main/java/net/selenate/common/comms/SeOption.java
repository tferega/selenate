package net.selenate.common.comms;

public class SeOption extends SeComms {
  private static final long serialVersionUID = 1L;

  public final SeElement element;

  public SeOption(final SeElement element) {
    this.element = element;
  }

  @Override
  public String toString() {
    return String.format("SeOption(%s)", element.getDesc());
  }
}
