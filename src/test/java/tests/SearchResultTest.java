package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.SearchResultsPage;
import utils.Config;

public class SearchResultTest extends BaseTest {

    // TC04 - Click the first result and open its profile
    @Test(description = "TC04 - Open the first search result")
    public void openFirstResult() {

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

        // Click the first profile result and get its URL
        String profileUrl = results.clickFirstResultAndGetHref();

        // Wait until the browser opens a profile page
        boolean profileOpened = false;

        try {
            profileOpened = new org.openqa.selenium.support.ui.WebDriverWait(
                    driver,
                    java.time.Duration.ofSeconds(15)
            ).until(
                    org.openqa.selenium.support.ui.ExpectedConditions.urlContains("/in/")
            );
        } catch (org.openqa.selenium.TimeoutException e) {
            profileOpened = false;
        }

        Assert.assertTrue(
                profileOpened,
                "Failed to open the first search result profile"
        );

        // Verify that the opened profile matches the clicked result
        String currentUrl = driver.getCurrentUrl();

        Assert.assertTrue(
                currentUrl.contains(profileUrl),
                "The opened profile does not match the clicked result"
        );

        System.out.println("Successfully opened profile: " + currentUrl);
    }
}