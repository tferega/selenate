package net.selenate.common.comms.req;

import net.selenate.common.comms.SeNavigateDirection;
import net.selenate.common.exceptions.SeNullArgumentException;

public final class SeReqWindowNavigate implements SeCommsReq {
  private static final long serialVersionUID = 45749879L;

  private SeNavigateDirection direction;

  public SeReqWindowNavigate(final SeNavigateDirection direction) {
    this.direction = direction;
    validate();
  }

  public SeNavigateDirection getDirection() {
    return direction;
  }

  public SeReqWindowNavigate withDirection(final SeNavigateDirection newDirection) {
    return new SeReqWindowNavigate(newDirection);
  }

  private void validate() {
    if (direction == null) {
      throw new SeNullArgumentException("Direction");
    }
  }

  @Override
  public String toString() {
    return String.format("SeReqWindowNavigate(%s)", direction);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((direction == null) ? 0 : direction.hashCode());
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
    SeReqWindowNavigate other = (SeReqWindowNavigate) obj;
    if (direction != other.direction)
      return false;
    return true;
  }
}
