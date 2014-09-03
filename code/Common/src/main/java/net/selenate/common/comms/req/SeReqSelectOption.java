package net.selenate.common.comms.req;

import net.selenate.common.comms.SeAddress;
import net.selenate.common.comms.SeElementSelectMethod;
import net.selenate.common.comms.SeFrameElementSelector;
import net.selenate.common.comms.SeOptionSelectMethod;
import net.selenate.common.comms.SeOptionSelector;

public class SeReqSelectOption implements SeCommsReq {
  private static final long serialVersionUID = 1L;

  public final SeAddress             address;
  public final SeElementSelectMethod parentMethod;
  public final String                parentQuery;
  public final SeOptionSelectMethod  optionMethod;
  public final String                optionQuery;

  public SeReqSelectOption(
      final SeAddress             address,
      final SeElementSelectMethod parentMethod,
      final String                parentQuery,
      final SeOptionSelectMethod  optionMethod,
      final String                optionQuery) {
    if (address == null) {
      throw new IllegalArgumentException("Address cannot be null!");
    }
    if (parentMethod == null) {
      throw new IllegalArgumentException("Parent method cannot be null!");
    }
    if (parentQuery == null) {
      throw new IllegalArgumentException("Parent query cannot be null!");
    }
    if (optionMethod == null) {
      throw new IllegalArgumentException("Option method cannot be null!");
    }
    if (optionQuery == null) {
      throw new IllegalArgumentException("Option query cannot be null!");
    }

    this.address      = address;
    this.parentMethod = parentMethod;
    this.parentQuery  = parentQuery;
    this.optionMethod = optionMethod;
    this.optionQuery  = optionQuery;
  }

  public SeReqSelectOption(
      final SeFrameElementSelector parentSelector,
      final SeOptionSelector       optionSelector) {
    if (parentSelector == null) {
      throw new IllegalArgumentException("Parent selector cannot be null!");
    }
    if (optionSelector == null) {
      throw new IllegalArgumentException("Option selector cannot be null!");
    }

    this.address      = parentSelector.address;
    this.parentMethod = parentSelector.method;
    this.parentQuery  = parentSelector.query;
    this.optionMethod = optionSelector.method;
    this.optionQuery  = optionSelector.query;
  }

  @Override
  public String toString() {
    return String.format("SeReqSelectOption(%s, %s, %s, %s, %s)",
        address, parentMethod, parentQuery, optionMethod, optionQuery);
  }
}
