package net.selenate.demo;

import net.selenate.common.comms.SePage;

import java.util.List;
import java.util.ArrayList;
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
    System.out.println("Starting session...");
    final String sessionId = UUID.randomUUID().toString();
    final ActorRef session = ActorFactory.createSession(sessionId, RECORD_SESSION);
    final Browser browser = new Browser(session);
    System.out.println("Session ID: " + sessionId);

    try {
      final boolean isLoginOk = ebayLogin(browser, USER_ID, PASS);
      if (isLoginOk) {
        final String price = ebayGetFirstPrice(browser, QUERY);
        System.out.println("Price for " + QUERY + ": " + price);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      killSession(browser);
    }
  }

  private static boolean ebayLogin(
      final Browser browser,
      final String userID,
      final String pass) throws IOException {
    System.out.println("Opening Ebay...");
    browser.open("http://www.ebay.com/");
    browser.waitFor(Homepage.page);

    System.out.println("Opening login page...");
    browser.findElement(Homepage.login).click();
    browser.waitFor(LoginPage.page);

    System.out.println("Entering credentials and logging in...");
    browser.findElement(LoginPage.userID).textInput(false, userID);
    browser.findElement(LoginPage.pass).textInput(false, pass);
    browser.findElement(LoginPage.loginBtn).click();
    final List<SePage> pages = new ArrayList<>();
    pages.addAll(InvalidLoginPage.page);
    pages.addAll(LandingPage.page);
    final SePage openedPage = browser.waitFor(pages);
    switch(openedPage.getName()) {
      case "InvalidLoginPage":
        final String errorMessage = browser.findElement(InvalidLoginPage.errorMessage).getElement().getText();
        System.out.println("Invalid login: " + errorMessage);
        return false;
      case "LandingPage":
        System.out.println("Login successful!");
        return true;
      default:
        System.out.println("Unexpected page!");
        return false;
    }
  }

  private static String ebayGetFirstPrice(
      final Browser browser,
      final String query) throws IOException {
    System.out.println("Entering query and searching...");
    browser.findElement(LandingPage.searchBox).textInput(false, query);
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

  private static void killSession(final Browser browser) throws IOException {
    System.out.print("Enter newline to kill session: ");
    System.in.read();

    browser.quit();
    ActorFactory.shutdown();
  }
}
