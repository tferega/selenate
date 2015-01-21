package net.selenate.common.comms.req;

import net.selenate.common.SelenateUtils;

public final class SeReqSystemInput implements SeCommsReq {
  private static final long serialVersionUID = 45749879L;

  private final String input;

  public SeReqSystemInput(final String input) {
    this.input = SelenateUtils.guardNull(input, "Input");
  }

  public String getInput() {
    return input;
  }

  public SeReqSystemInput withInput(final String newInput) {
    return new SeReqSystemInput(newInput);
  }

  @Override
  public String toString() {
    return String.format("SeReqSystemInput(%s)", input);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((input == null) ? 0 : input.hashCode());
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
    SeReqSystemInput other = (SeReqSystemInput) obj;
    if (input == null) {
      if (other.input != null)
        return false;
    } else if (!input.equals(other.input))
      return false;
    return true;
  }
}