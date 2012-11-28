package net.selenate.common.user;

public class Position {
  public final int x;
  public final int y;

  public Position(
      final int x,
      final int y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public String toString() {
    return String.format("Position[%d, %d]", x, y);
  }
}
