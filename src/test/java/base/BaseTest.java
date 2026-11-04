package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.LoginPage;
import pages.HomePage;
import utils.Config;

public class BaseTest {

    protected WebDriver driver;

    // Open Chrome before each test
    @BeforeMethod
    public void setUp() {

        driver = new ChromeDriver();

        driver.manage().window().maximize();

        System.out.println("Chrome browser opened successfully.");
    }

    // Close Chrome after each test
    @AfterMethod
    public void tearDown() {

        if (driver != null) {
            //driver.quit();
            System.out.println("Chrome browser closed.");
        }
    }

    // Return the WebDriver instance
    public WebDriver getDriver() {
        return driver;
    }

    // Reusable method to log in with a valid account
    public HomePage loginAsValidUser() {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.open();

        HomePage home = loginPage.login(
                Config.get("linkedin.username"),
                Config.get("linkedin.password")
        );

        loginPage.waitForManualVerificationIfNeeded();

        return home;
    }
}
