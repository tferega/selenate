package net.selenate.client.user;

import akka.actor.ActorRef;
import java.io.IOException;

import net.selenate.common.comms.SeElementSelector;
import net.selenate.common.comms.SeOptionSelectMethod;
import net.selenate.common.comms.SeOptionSelector;
import net.selenate.common.comms.SeSelect;
import net.selenate.common.comms.req.SeReqSelectChoose;
import net.selenate.common.comms.res.SeResSelectChoose;

public class ActorSelect extends ActorElement {
  private final SeSelect select;

  public ActorSelect(final ActorRef session, final SeSelect select) {
    super(session, select.getElement());
    this.select = select;
  }

  public SeSelect getSelect() {
    return select;
  }

  public void selectByIndex(int index) throws IOException {
    final SeOptionSelector optionSelector = new SeOptionSelector(SeOptionSelectMethod.INDEX, String.valueOf(index));
    choose(select.getElement().getSelector(), optionSelector);
  }

  public void choose(final SeElementSelector parentSelector, final SeOptionSelector optionSelector) throws IOException {
    typedBlock(new SeReqSelectChoose(parentSelector, optionSelector), SeResSelectChoose.class);
  }
}
