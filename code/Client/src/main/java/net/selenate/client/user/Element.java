package net.selenate.client.user;

import akka.actor.ActorRef;
import akka.util.Timeout;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import net.selenate.common.comms.SeElement;
import net.selenate.common.comms.req.SeReqElementClick;
import net.selenate.common.comms.req.SeReqElementGetAttributes;
import net.selenate.common.comms.req.SeReqElementTextInput;
import net.selenate.common.comms.req.SeReqSelectFindList;
import net.selenate.common.comms.res.SeResElementClick;
import net.selenate.common.comms.res.SeResElementGetAttributes;
import net.selenate.common.comms.res.SeResElementTextInput;
import net.selenate.common.comms.res.SeResSelectFindList;

public class Element extends ActorBase {
  private final SeElement element;

  public Element(
      final Timeout timeout,
      final ActorRef session,
      final SeElement element) {
    super(timeout, session);
    this.element = element;
  }

  public Select toSelect() throws IOException {
    final SeResSelectFindList res = typedBlock(new SeReqSelectFindList(element.getSelector()), SeResSelectFindList.class);
    return new Select(timeout, super.session, res.getSelect().get(0));
  }

  public String getAttribute(final String attributeName) throws IOException {
    return getAttributeList(attributeName).get(attributeName);
  }

  public Map<String, String> getAttributeList(final String... attributeNameList) throws IOException {
    final Map<String, String> selectedAttrs = new HashMap<>();
    final Map<String, String> attrs = getAttributes();
    for (final String attrName : attributeNameList) {
      final String attr = attrs.get(attrName);
      if (attr == null) { throw new IOException(String.format("Required attribute '%s' not found in element %s!", attrName, element.getSelector())); }
      selectedAttrs.put(attrName, attr);
    }
    return selectedAttrs;
  }

  public Map<String, String> getAttributes() throws IOException {
    final SeResElementGetAttributes res = typedBlock(new SeReqElementGetAttributes(element.getSelector()), SeResElementGetAttributes.class);
    return res.getAttributes();
  }

  public void checkboxSetPlain(final boolean targetIsSelected) throws IOException {
    if (element.isSelected() != targetIsSelected) {
      click();
    }
  }

  public void checkboxSetFancy(final boolean targetIsSelected) throws IOException {
    final boolean isSelected = getAttribute("class").contains("checkbox-active");
    if (isSelected != targetIsSelected) {
      click();
    }
  }

  public void click() throws IOException {
    typedBlock(new SeReqElementClick(element.getSelector()), SeResElementClick.class);
  }

  public String getText() {
    return element.getText().trim();
  }

  public void clearText() throws IOException {
    setText("");
  }

  public void setText(final String text) throws IOException {
    typedBlock(new SeReqElementTextInput(element.getSelector(), false, text), SeResElementTextInput.class);
  }
}
