package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import utils.Config;

public class LoginTest extends BaseTest {

    // TC01 - Valid login
    @Test(description = "TC01 - Valid login")
    public void validLogin() {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.open();

        HomePage home = loginPage.login(
                Config.get("linkedin.username"),
                Config.get("linkedin.password")
        );

        loginPage.waitForManualVerificationIfNeeded();

        Assert.assertTrue(
                home.isLoggedIn(),
                "Feed/search box not shown after login"
        );

        Assert.assertTrue(
                driver.getCurrentUrl().contains("/feed"),
                "URL is not the feed page"
        );
    }

    // TC02 - Invalid password
    @Test(description = "TC02 - Invalid password shows error")
    public void invalidPassword() {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.open();

        loginPage.login(
                Config.get("linkedin.username"),
                "WrongPassword!123"
        );

        Assert.assertTrue(
                loginPage.isErrorDisplayed(),
                "Error message was not displayed"
        );

        System.out.println("Invalid login test passed.");
    }
}