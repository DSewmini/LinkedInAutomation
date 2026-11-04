
package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.SearchResultsPage;
import utils.Config;

public class SearchTest extends BaseTest {

    // TC03 - Search shows results
    @Test(description = "TC03 - Search shows a results page")
    public void searchShowsResults() {

        loginAsValidUser();

        HomePage home = new HomePage(driver);

        Assert.assertTrue(
                home.isLoggedIn(),
                "User is not logged in"
        );

        SearchResultsPage results =
                home.search(Config.get("search.term"));

        Assert.assertTrue(
                results.isResultsPageDisplayed(),
                "Search results page was not displayed"
        );

        Assert.assertTrue(
                driver.getCurrentUrl().contains("/search/results"),
                "Search URL is incorrect"
        );

        results.debugPeopleFilter();

        Assert.assertTrue(
                results.hasVisibleResult(),
                "No visible person result was found"
        );
    }
}
