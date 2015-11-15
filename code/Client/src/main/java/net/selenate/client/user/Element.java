package net.selenate.client.user;

import akka.actor.ActorRef;
import java.io.IOException;
import java.util.Map;
import net.selenate.common.comms.SeElement;
import net.selenate.common.comms.req.*;
import net.selenate.common.comms.res.*;

public class Element extends ActorBase {
  private final SeElement element;

  public Element(final ActorRef session, final SeElement element) {
    super(session);
    this.element = element;
  }

  public SeElement getElement() {
    return element;
  }

  public Select toSelect() throws IOException {
    final SeResSelectFindList res = typedBlock(new SeReqSelectFindList(element.getSelector()), SeResSelectFindList.class);
    return new Select(super.session, res.getSelect().get(0));
  }

  public void textInput(
      final boolean isAppend,
      final String text) throws IOException {
    typedBlock(new SeReqElementTextInput(element.getSelector(), isAppend, text), SeResElementTextInput.class);
  }

  public void click() throws IOException {
    typedBlock(new SeReqElementClick(element.getSelector()), SeResElementClick.class);
  }

  public Map<String, String> getAttributes() throws IOException {
    final SeResElementGetAttributes res = typedBlock(new SeReqElementGetAttributes(element.getSelector()), SeResElementGetAttributes.class);
    return res.getAttributes();
  }

  public byte[] capture() throws IOException {
    final SeResElementCapture res = typedBlock(new SeReqElementCapture(element.getSelector()), SeResElementCapture.class);
    return res.getBody();
  }
}
