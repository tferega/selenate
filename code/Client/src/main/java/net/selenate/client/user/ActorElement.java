package net.selenate.client.user;

import akka.actor.ActorRef;

import java.io.IOException;
import java.util.Map;

import net.selenate.common.comms.SeElement;
import net.selenate.common.comms.req.SeReqElementCapture;
import net.selenate.common.comms.req.SeReqElementClick;
import net.selenate.common.comms.req.SeReqElementGetAttributes;
import net.selenate.common.comms.req.SeReqElementTextInput;
import net.selenate.common.comms.req.SeReqSelectFindList;
import net.selenate.common.comms.res.SeResElementCapture;
import net.selenate.common.comms.res.SeResElementClick;
import net.selenate.common.comms.res.SeResElementGetAttributes;
import net.selenate.common.comms.res.SeResElementTextInput;
import net.selenate.common.comms.res.SeResSelectFindList;

public class ActorElement extends ActorBase {
  private final SeElement element;

  public ActorElement(final ActorRef session, final SeElement element) {
    super(session);
    this.element = element;
  }

  public SeElement getElement() {
    return element;
  }

  public ActorSelect toSelect() throws IOException {
    final SeResSelectFindList res = typedBlock(new SeReqSelectFindList(element.getSelector()), SeResSelectFindList.class);
    return new ActorSelect(super.session, res.getSelect().get(0));
  }

  public void clearText() throws IOException {
    typedBlock(new SeReqElementTextInput(element.getSelector(), false, ""), SeResElementTextInput.class);
  }

  public void appendText(String text) throws IOException {
    typedBlock(new SeReqElementTextInput(element.getSelector(), true, text), SeResElementTextInput.class);
  }

  public void setText(String text) throws IOException {
    typedBlock(new SeReqElementTextInput(element.getSelector(), false, text), SeResElementTextInput.class);
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
