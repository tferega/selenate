package net.selenate.common.comms.res;

public class SeResWaitFor implements SeCommsRes {
  private static final long serialVersionUID = 1L;

  public final boolean isSuccessful;
  public final String  foundName;

  public SeResWaitFor(
      final boolean isSuccessful,
      final String  foundName) {
    if (isSuccessful && foundName == null) {
      throw new IllegalArgumentException("When successful, found name cannot be null!");
    }
    if (!isSuccessful && foundName != null) {
      throw new IllegalArgumentException("When unsuccessful, found name must be null!");
    }

    this.isSuccessful = isSuccessful;
    this.foundName    = foundName;
  }

  @Override
  public String toString() {
    return String.format("SeResWaitFor(%s, %s)", isSuccessful, foundName);
  }
}
