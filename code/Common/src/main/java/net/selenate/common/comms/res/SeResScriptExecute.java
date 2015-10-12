package net.selenate.common.comms.res;


import net.selenate.common.SelenateUtils;

public final class SeResScriptExecute implements SeCommsRes {
  private static final long serialVersionUID = 45749879L;

  private final String result;

  public SeResScriptExecute(final String result) {
    this.result = SelenateUtils.guardNull(result, "Result");
  }

  public String getResult() {
    return result;
  }

  public SeResScriptExecute withResult(final String newResult) {
    return new SeResScriptExecute(newResult);
  }

  @Override
  public String toString() {
    return String.format("SeResScriptExecute(%s)", result);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result
        + ((this.result == null) ? 0 : this.result.hashCode());
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
    SeResScriptExecute other = (SeResScriptExecute) obj;
    if (result == null) {
      if (other.result != null)
        return false;
    } else if (!result.equals(other.result))
      return false;
    return true;
  }
}