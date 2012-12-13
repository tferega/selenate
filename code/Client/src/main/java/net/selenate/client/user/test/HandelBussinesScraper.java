package net.selenate.client.user.test;

import java.io.IOException;

import net.selenate.common.user.ElementSelectMethod;
import net.selenate.common.user.IBrowser;
import net.selenate.common.user.IElement;

public class HandelBussinesScraper {
  public static String base = "https://secure.handelsbanken.se/bb/glss/servlet/ssco_auth2?appAction=doAuthentication&path=ssse&entryId=corpcapse&language=sv&country=SE";

  private final IBrowser browser;

  public HandelBussinesScraper(IBrowser browser) {
    this.browser = browser;
  }

  public String start() throws IOException {
    browser.open(base);
    final IElement challengeElem = browser.findElement(ElementSelectMethod.CLASS_NAME, "boxedcode");
    return challengeElem.getText();
  }


  public void login(String personalNumber, String code) throws IOException {
    final IElement pnumInput = browser.findElement(ElementSelectMethod.NAME, "CAP_USID");
    final IElement codeInput = browser.findElement(ElementSelectMethod.NAME, "CAP_RESPONSE");
    final IElement loginButton = browser.findElement(ElementSelectMethod.NAME, "subm");

    pnumInput.setText(personalNumber);
    codeInput.setText(code);
    loginButton.click();
  }
}
