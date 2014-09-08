package net.selenate.common.comms.req;

public final class SeReqSystemInput implements SeCommsReq {
  private static final long serialVersionUID = 45749879L;

  private final String input;

  public SeReqSystemInput(final String input) {
    this.input = input;
    validate();
  }

  public String getInput() {
    return input;
  }

  public SeReqSystemInput withInput(final String newInput) {
    return new SeReqSystemInput(newInput);
  }

  private void validate() {
    if(input == null) {
      throw new IllegalArgumentException("Input cannot be null!");
    }
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
