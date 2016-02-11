package net.selenate.client.user;

import akka.actor.ActorRef;
import akka.util.Timeout;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import net.selenate.common.comms.SeOptionSelectMethod;
import net.selenate.common.comms.SeOptionSelector;
import net.selenate.common.comms.SeSelect;
import net.selenate.common.comms.req.SeReqSelectChoose;
import net.selenate.common.comms.req.SeReqSelectFindOptionList;
import net.selenate.common.comms.res.SeResSelectChoose;
import net.selenate.common.comms.res.SeResSelectFindOptionList;

public class Select extends Element {
  private final SeSelect select;

  public Select(
      final Timeout timeout,
      final ActorRef session,
      final SeSelect select) {
    super(timeout, session, select.getElement());
    this.select = select;
  }

  public void chooseText(final String text) throws IOException {
    final SeOptionSelector optionSelector = new SeOptionSelector(SeOptionSelectMethod.VISIBLE_TEXT, text);
    typedBlock(new SeReqSelectChoose(select.getElement().getSelector(), optionSelector), SeResSelectChoose.class);
  }

  public List<String> getAllOptions() throws IOException {
    final SeResSelectFindOptionList res =  typedBlock(new SeReqSelectFindOptionList(select.getElement().getSelector()), SeResSelectFindOptionList.class);
    return res.getOptionList()
        .stream()
        .map(option -> option.getElement().getText())
        .collect(Collectors.toList());
  }
}
