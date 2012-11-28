package net.selenate.common.user;

public class Location {
  public final int width;
  public final int height;

  public Location(
      final int width,
      final int height) {
    this.width  = width;
    this.height = height;
  }

  @Override
  public String toString() {
    return String.format("Position[%d, %d]", width, height);
  }
}
