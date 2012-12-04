package net.selenate.client.user;

import java.io.IOException;

import net.selenate.common.comms.req.*;
import net.selenate.common.comms.res.*;
import net.selenate.common.user.ElementSelector;
import net.selenate.common.user.IAlert;
import net.selenate.common.user.IBrowser;
import net.selenate.common.user.IElement;
import net.selenate.common.user.Location;
import net.selenate.common.user.Position;

import akka.actor.ActorRef;

public class ActorBrowser extends ActorBase implements IBrowser {
  public ActorBrowser(final ActorRef session) {
    super(session);
  }

  @Override
  public void open(final String url) throws IOException {
    typedBlock(new SeReqGet(url), SeResGet.class);
  }

  @Override
  public void capture(final String name) throws IOException {
    typedBlock(new SeReqCapture(name), SeResCapture.class);
  }

  @Override
  public String executeScript(final String javascript) throws IOException {
    final SeResExecuteScript res = typedBlock(new SeReqExecuteScript(javascript), SeResExecuteScript.class);
    return res.result;
  }

  @Override
  public void quit() throws IOException {
    typedBlock(new SeReqQuit(), SeResQuit.class);
  }

  @Override
  public IElement findElement(
      final ElementSelector method,
      final String query)
      throws IOException {
    final SeReqSelectMethod reqMethod;
    switch (method) {
      case CLASS_NAME:        reqMethod = SeReqSelectMethod.CLASS_NAME;        break;
      case CSS_SELECTOR:      reqMethod = SeReqSelectMethod.CSS_SELECTOR;      break;
      case ID:                reqMethod = SeReqSelectMethod.ID;                break;
      case LINK_TEXT:         reqMethod = SeReqSelectMethod.LINK_TEXT;         break;
      case NAME:              reqMethod = SeReqSelectMethod.NAME;              break;
      case PARTIAL_LINK_TEXT: reqMethod = SeReqSelectMethod.PARTIAL_LINK_TEXT; break;
      case TAG_NAME:          reqMethod = SeReqSelectMethod.TAG_NAME;          break;
      case UUID:              reqMethod = SeReqSelectMethod.UUID;              break;
      case XPATH:             reqMethod = SeReqSelectMethod.XPATH;             break;
      default:                throw new RuntimeException("Unexpected error!");
    }

    final SeResElement res = typedBlock(new SeReqElement(reqMethod, query), SeResElement.class);

    return new ActorElement(
        session,
        res.uuid,
        new Position(res.posX, res.posY),
        new Location(res.width, res.height),
        res.name,
        res.text,
        res.isDisplayed,
        res.isEnabled,
        res.isSelected,
        res.attributeList,
        null);
  }

  @Override
  public boolean isAlert() throws IOException {
    throw new IllegalArgumentException("Not supported");
  }

  @Override
  public IAlert findAlert() throws IOException {
    final SeResFindAlert alert = typedBlock(new SeReqFindAlert(), SeResFindAlert.class);
    return new ActorAlert(session, alert.text);
  }
}
