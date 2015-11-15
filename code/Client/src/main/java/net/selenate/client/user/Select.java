package net.selenate.client.user;

import akka.actor.ActorRef;
import java.io.IOException;
import java.util.List;
import net.selenate.common.comms.SeOption;
import net.selenate.common.comms.SeOptionSelector;
import net.selenate.common.comms.SeSelect;
import net.selenate.common.comms.req.SeReqSelectChoose;
import net.selenate.common.comms.req.SeReqSelectFindOptionList;
import net.selenate.common.comms.res.SeResSelectChoose;
import net.selenate.common.comms.res.SeResSelectFindOptionList;

public class Select extends Element {
  private final SeSelect select;

  public Select(final ActorRef session, final SeSelect select) {
    super(session, select.getElement());
    this.select = select;
  }

  public SeSelect getSelect() {
    return select;
  }

  public void choose(final SeOptionSelector optionSelector) throws IOException {
    typedBlock(new SeReqSelectChoose(select.getElement().getSelector(), optionSelector), SeResSelectChoose.class);
  }

  public List<SeOption> getAllOptions() throws IOException {
    final SeResSelectFindOptionList res =  typedBlock(new SeReqSelectFindOptionList(select.getElement().getSelector()), SeResSelectFindOptionList.class);
    return res.getOptionList();
  }
}
