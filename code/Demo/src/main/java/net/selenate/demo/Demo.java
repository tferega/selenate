package net.selenate.demo;

import akka.actor.ActorRef;
import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;
import net.selenate.client.user.*;
import net.selenate.common.comms.res.SeResBrowserCapture;
import static net.selenate.demo.Pages.*;

public class Demo {
  private static final String USER_ID = "";
  private static final String PASS    = "";
  private static final String QUERY   = "";

  private static final boolean RECORD_SESSION  = true;
  private static final boolean TAKE_SCREENSHOT = true;

  public static void main(String[] args) throws IOException {
    final String sessionID = ebayLogin(USER_ID, PASS);
    System.out.println("Session ID: " + sessionID);

    //final String sessionID = "";
    //final String sessionID = "";
    final String price = ebayGetFirstPrice(sessionID, QUERY);
    System.out.println("Price for " + QUERY + ": " + price);

    killSession(sessionID);
  }

  private static ActorBrowser connect(final String sessionName) throws IOException {
    final ActorRef session = ActorFactory.waitForSession(sessionName, RECORD_SESSION);
    final ActorBrowser browser = new ActorBrowser(session);
    return browser;
  }

  private static String ebayLogin(
      final String userID,
      final String pass) throws IOException {
    final String sessionID = UUID.randomUUID().toString();

    System.out.println("Starting session " + sessionID);
    final ActorBrowser browser = connect(sessionID);

    System.out.println("Opening Ebay...");
    browser.open("http://www.ebay.com/");
    browser.waitFor(Homepage.page);

    System.out.println("Opening login page...");
    browser.findElement(Homepage.login).click();
    browser.waitFor(LoginPage.page);

    System.out.println("Entering credentials and logging in...");
    browser.findElement(LoginPage.userID).setText(userID);
    browser.findElement(LoginPage.pass).setText(pass);
    browser.findElement(LoginPage.loginBtn).click();
    final String openedPage = browser.waitForAny(InvalidLoginPage.page, LandingPage.page);
    switch(openedPage) {
      case "InvalidLoginPage":
        System.out.println("Invalid login!");
        final String errorMessage = browser.findElement(InvalidLoginPage.errorMessage).getElement().getText();
        browser.quit();
        ActorFactory.shutdown();
        return "Invalid login: " + errorMessage;
      case "LandingPage":
        System.out.println("Login successful!");
        // Continue...
        break;
      default:
        System.out.println("Unexpected error!");
        browser.quit();
        ActorFactory.shutdown();
        return "Unexpected page: " + openedPage;
    }

    return sessionID;
  }

  private static String ebayGetFirstPrice(final String sessionID, final String query) throws IOException {
    final ActorRef session = ActorFactory.waitForActor("session-factory/" + sessionID);
    final ActorBrowser browser = new ActorBrowser(session);

    System.out.println("Entering query and searching...");
    browser.findElement(LandingPage.searchBox).setText(query);
    browser.findElement(LandingPage.searchBtn).click();
    browser.waitFor(ResultsPage.page);

    System.out.println("Opening first search result...");
    browser.findElement(ResultsPage.resultLinks).click();
    browser.waitFor(ItemPage.page);

    if (TAKE_SCREENSHOT) {
      System.out.println("Capturing article screenshot and HTML");
      final SeResBrowserCapture capture = browser.capture("PricePage");
      final String html       = capture.getWindowList().get(0).getHtml();
      final byte[] screenshot = capture.getWindowList().get(0).getScreenshot();
      Files.write(Paths.get("V:\\capture-source.html"), html.getBytes());
      Files.write(Paths.get("V:\\capture-screenshot.png"), screenshot);
    }

    System.out.println("Scraping price...");
    final String price = browser.findElement(ItemPage.price).getElement().getText();

    return price;
  }

  private static void killSession(final String sessionID) throws IOException {
    final ActorRef session = ActorFactory.waitForActor("session-factory/" + sessionID);
    final ActorBrowser browser = new ActorBrowser(session);

    System.out.print("Enter newline to kill session: ");
    System.in.read();

    browser.quit();
    ActorFactory.shutdown();
  }
}
