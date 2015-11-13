package net.selenate.demo;

import net.selenate.common.comms.SeElementVisibility;

import java.util.Arrays;
import net.selenate.common.comms.SeElementSelectMethod;
import net.selenate.common.comms.SeElementSelector;
import net.selenate.common.comms.SePage;

public class Pages {
  private static SePage createPage(final String name, final SeElementSelector... selectors) {
    return new SePage(name, Arrays.asList(selectors));
  }

  private static SeElementSelector createSelector(final String query) {
    return new SeElementSelector(SeElementSelectMethod.CSS_SELECTOR, SeElementVisibility.ANY, query);
  }

  public static class Homepage {
    public static SeElementSelector login = createSelector("a[href*=\"signin\"]");
    public static SePage page = createPage("Homepage", login);
  }

  public static class LoginPage {
    public static SeElementSelector userID = createSelector("input#userid");
    public static SeElementSelector pass = createSelector("input#pass");
    public static SeElementSelector loginBtn = createSelector("input#sgnBt");
    public static SePage page = createPage("LoginPage", userID, pass, loginBtn);
  }

  public static class InvalidLoginPage {
    public static SeElementSelector errorMessage = createSelector("span#errf");
    public static SePage page = createPage("InvalidLoginPage", errorMessage);
  }

  public static class LandingPage {
    public static SeElementSelector searchBox = createSelector("input#gh-ac");
    public static SeElementSelector searchBtn = createSelector("input#gh-btn");
    public static SePage page = createPage("LandingPage", searchBox, searchBtn);
  }

  public static class ResultsPage {
    public static SeElementSelector resultsTable = createSelector("div#Results");
    public static SeElementSelector resultLinks  = createSelector("ul#ListViewInner > li > h3 > a");
    public static SePage page = createPage("ResultsPage", resultsTable, resultLinks);
  }

  public static class ItemPage {
    public static SeElementSelector bidBox = createSelector("form[name=\"viactiondetails\"]");
    public static SeElementSelector price  = createSelector("span[itemprop=\"price\"]");
    public static SePage page = createPage("ItemPage", bidBox, price);
  }
}
