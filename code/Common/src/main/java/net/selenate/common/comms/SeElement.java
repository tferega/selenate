package net.selenate.common.comms;

import net.selenate.common.comms.SeAddress;

public final class SeElement implements SeComms {
  private static final long serialVersionUID = 45749879L;

  private final String    uuid;
  private final int       posX;
  private final int       posY;
  private final int       width;
  private final int       height;
  private final String    name;
  private final String    text;
  private final boolean   isDisplayed;
  private final boolean   isEnabled;
  private final boolean   isSelected;
  private final SeAddress address;

  public SeElement(
      final String    uuid,
      final int       posX,
      final int       posY,
      final int       width,
      final int       height,
      final String    name,
      final String    text,
      final boolean   isDisplayed,
      final boolean   isEnabled,
      final boolean   isSelected,
      final SeAddress address) {
    this.uuid        = uuid;
    this.posX        = posX;
    this.posY        = posY;
    this.width       = width;
    this.height      = height;
    this.name        = name;
    this.text        = text;
    this.isDisplayed = isDisplayed;
    this.isEnabled   = isEnabled;
    this.isSelected  = isSelected;
    this.address     = address;
    validate();
  }

  public String getUuid() {
    return uuid;
  }

  public int getPosX() {
    return posX;
  }

  public int getPosY() {
    return posY;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public String getName() {
    return name;
  }

  public String getText() {
    return text;
  }

  public boolean isDisplayed() {
    return isDisplayed;
  }

  public boolean isEnabled() {
    return isEnabled;
  }

  public boolean isSelected() {
    return isSelected;
  }

  public SeAddress getAddress() {
    return address;
  }

  public SeElement withUuid(final String newUUID) {
    return new SeElement(newUUID, this.posX, this.posY, this.width, this.height, this.name, this.text, this.isDisplayed, this.isEnabled, this.isSelected, this.address);
  }

  public SeElement withPosX(final int newPosX) {
    return new SeElement(this.uuid, newPosX, this.posY, this.width, this.height, this.name, this.text, this.isDisplayed, this.isEnabled, this.isSelected, this.address);
  }

  public SeElement withPosY(final int newPosY) {
    return new SeElement(this.uuid, this.posX, newPosY, this.width, this.height, this.name, this.text, this.isDisplayed, this.isEnabled, this.isSelected, this.address);
  }

  public SeElement withWidth(final int newWidth) {
    return new SeElement(this.uuid, this.posX, this.posY, newWidth, this.height, this.name, this.text, this.isDisplayed, this.isEnabled, this.isSelected, this.address);
  }

  public SeElement withHeight(final int newHeight) {
    return new SeElement(this.uuid, this.posX, this.posY, this.width, newHeight, this.name, this.text, this.isDisplayed, this.isEnabled, this.isSelected, this.address);
  }

  public SeElement withName(final String newName) {
    return new SeElement(this.uuid, this.posX, this.posY, this.width, this.height, newName, this.text, this.isDisplayed, this.isEnabled, this.isSelected, this.address);
  }

  public SeElement withText(final String newText) {
    return new SeElement(this.uuid, this.posX, this.posY, this.width, this.height, this.name, newText, this.isDisplayed, this.isEnabled, this.isSelected, this.address);
  }

  public SeElement withDisplayed(final boolean newDisplayed) {
    return new SeElement(this.uuid, this.posX, this.posY, this.width, this.height, this.name, this.text, newDisplayed, this.isEnabled, this.isSelected, this.address);
  }

  public SeElement withEnabled(final boolean newEnabled) {
    return new SeElement(this.uuid, this.posX, this.posY, this.width, this.height, this.name, this.text, this.isDisplayed, newEnabled, this.isSelected, this.address);
  }

  public SeElement withSelected(final boolean newSelected) {
    return new SeElement(this.uuid, this.posX, this.posY, this.width, this.height, this.name, this.text, this.isDisplayed, this.isEnabled, newSelected, this.address);
  }

  public SeElement withAddress(final SeAddress newAddress) {
    return new SeElement(this.uuid, this.posX, this.posY, this.width, this.height, this.name, this.text, this.isDisplayed, this.isEnabled, this.isSelected, newAddress);
  }

  private void  validate() {
    if (uuid == null) {
      throw new IllegalArgumentException("UUID cannot be null!");
    }
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null!");
    }
    if (text == null) {
      throw new IllegalArgumentException("Text cannot be null!");
    }
    if (address == null) {
      throw new IllegalArgumentException("Address cannot be null!");
    }
  }

  @Override
  public String toString() {
    return String.format("SeElement(%s, %d, %d, %d, %d, %s, %s, %b, %b, %b, %s)",
        uuid, posX, posY, width, height, name, text, isDisplayed, isEnabled, isSelected, address);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((address == null) ? 0 : address.hashCode());
    result = prime * result + height;
    result = prime * result + (isDisplayed ? 1231 : 1237);
    result = prime * result + (isEnabled ? 1231 : 1237);
    result = prime * result + (isSelected ? 1231 : 1237);
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + posX;
    result = prime * result + posY;
    result = prime * result + ((text == null) ? 0 : text.hashCode());
    result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
    result = prime * result + width;
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
    SeElement other = (SeElement) obj;
    if (address == null) {
      if (other.address != null)
        return false;
    } else if (!address.equals(other.address))
      return false;
    if (height != other.height)
      return false;
    if (isDisplayed != other.isDisplayed)
      return false;
    if (isEnabled != other.isEnabled)
      return false;
    if (isSelected != other.isSelected)
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (posX != other.posX)
      return false;
    if (posY != other.posY)
      return false;
    if (text == null) {
      if (other.text != null)
        return false;
    } else if (!text.equals(other.text))
      return false;
    if (uuid == null) {
      if (other.uuid != null)
        return false;
    } else if (!uuid.equals(other.uuid))
      return false;
    if (width != other.width)
      return false;
    return true;
  }
}
