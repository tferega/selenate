package net.selenate.client.user;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import akka.actor.ActorRef;

import net.selenate.common.comms.req.*;
import net.selenate.common.comms.res.*;
import net.selenate.common.user.IElement;
import net.selenate.common.user.ISelect;
import net.selenate.common.user.Location;
import net.selenate.common.user.Position;

public class ActorElement extends ActorBase implements IElement {
  private final String   uuid;
  private final Position pos;
  private final Location loc;
  private final String   name;
  private final String   text;
  private final boolean  isDisplayed;
  private final boolean  isEnabled;
  private final boolean  isSelected;
  private final Map<String, String> attributeList;
  private final List<IElement>      children;

  public ActorElement(
      final ActorRef session,
      final String   uuid,
      final Position pos,
      final Location loc,
      final String   name,
      final String   text,
      final boolean  isDisplayed,
      final boolean  isEnabled,
      final boolean  isSelected,
      final Map<String, String> attributeList,
      final List<IElement>      children) {
    super(session);

    if (uuid == null) {
      throw new IllegalArgumentException("UUID cannot be null!");
    }
    if (pos == null) {
      throw new IllegalArgumentException("Position cannot be null!");
    }
    if (loc == null) {
      throw new IllegalArgumentException("Location cannot be null!");
    }
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null!");
    }
    if (text == null) {
      throw new IllegalArgumentException("Text cannot be null!");
    }
    if (attributeList == null) {
      throw new IllegalArgumentException("Attribute list cannot be null!");
    }
    if (children == null) {
      throw new IllegalArgumentException("Children cannot be null!");
    }

    this.uuid          = uuid;
    this.pos           = pos;
    this.loc           = loc;
    this.name          = name;
    this.text          = text;
    this.isDisplayed   = isDisplayed;
    this.isEnabled     = isEnabled;
    this.isSelected    = isSelected;
    this.attributeList = attributeList;
    this.children      = children;
  }


  @Override
  public String getUuid() {
    return uuid;
  }

  @Override
  public Position getPos() {
    return pos;
  }

  @Override
  public Location getLoc() {
    return loc;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getText() {
    return text;
  }

  @Override
  public boolean getIsDisplayed() {
    return isDisplayed;
  }

  @Override
  public boolean getIsEnabled() {
    return isEnabled;
  }

  @Override
  public boolean getIsSelected() {
    return isSelected;
  }

  @Override
  public Map<String, String> getAttributeList() {
    return attributeList;
  }

  @Override
  public List<IElement> getChildren() {
    return children;
  }

  @Override
  public ISelect toSelect() {
    throw new IllegalArgumentException("Not supported");
  }

  @Override
  public void clearText() throws IOException {
    typedBlock(new SeReqClearText(SeReqSelectMethod.UUID, uuid), SeResClearText.class);
  }

  @Override
  public void appendText(String text) throws IOException {
    typedBlock(new SeReqAppendText(SeReqSelectMethod.UUID, uuid, text), SeResAppendText.class);
  }

  @Override
  public void setText(String text) throws IOException {
    clearText();
    appendText(text);
  }

  @Override
  public void click() throws IOException {
    typedBlock(new SeReqClick(SeReqSelectMethod.UUID, uuid), SeResClick.class);
  }
}
