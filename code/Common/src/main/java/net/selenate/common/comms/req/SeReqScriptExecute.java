package net.selenate.common.comms.req;

public final class SeReqScriptExecute implements SeCommsReq {
  private static final long serialVersionUID = 45749879L;

  private final String javascript;

  public SeReqScriptExecute(final String javascript) {
    this.javascript = javascript;
    validate();
  }

  public String getJavascript() {
    return javascript;
  }

  public SeReqScriptExecute withJavascript(final String newJavascript) {
    return new SeReqScriptExecute(newJavascript);
  }

  private void validate() {
    if (javascript == null) {
      throw new IllegalArgumentException("Javascript cannot be null!");
    }
  }

  @Override
  public String toString() {
    return String.format("SeReqScriptExecute(%s)", javascript);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result
        + ((javascript == null) ? 0 : javascript.hashCode());
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
    SeReqScriptExecute other = (SeReqScriptExecute) obj;
    if (javascript == null) {
      if (other.javascript != null)
        return false;
    } else if (!javascript.equals(other.javascript))
      return false;
    return true;
  }
}
