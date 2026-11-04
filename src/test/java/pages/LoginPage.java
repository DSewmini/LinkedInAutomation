
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage extends BasePage {

    private static final String URL =
            "https://www.linkedin.com/login";

    // Updated LinkedIn locators
    private final By usernameField =
            By.cssSelector("input[type='email'][autocomplete~='username']");

    private final By passwordField =
            By.cssSelector("input[autocomplete='current-password']");

    private final By signInButton =
            By.xpath("//button[normalize-space()='Sign in']");

    private final By errorMessage =
            By.xpath("//*[contains(normalize-space(), 'Wrong email or password.')]");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // Find the first visible matching input field
    private WebElement findVisibleInput(By locator) {

        System.out.println("Searching for: " + locator);

        return wait.until(d -> {

            var elements = d.findElements(locator);

            System.out.println("Matching elements: " + elements.size());

            for (WebElement element : elements) {

                try {

                    System.out.println(
                            "Displayed: " + element.isDisplayed()
                                    + " | Enabled: " + element.isEnabled()
                    );

                    if (element.isDisplayed() && element.isEnabled()) {
                        return element;
                    }

                } catch (org.openqa.selenium.StaleElementReferenceException e) {

                    System.out.println("Element changed during inspection.");

                }
            }

            return null;
        });
    }

    private WebElement findVisibleButton(By locator) {

        return wait.until(d -> {

            var elements = d.findElements(locator);

            for (WebElement element : elements) {

                try {

                    if (element.isDisplayed() && element.isEnabled()) {
                        return element;
                    }

                } catch (org.openqa.selenium.StaleElementReferenceException e) {
                    System.out.println("Button changed during inspection.");
                }
            }

            return null;
        });
    }

    public LoginPage open() {

        driver.get(URL);

        System.out.println("Current URL: " + driver.getCurrentUrl());
        System.out.println("Page title: " + driver.getTitle());

        findVisibleInput(usernameField);

        System.out.println("LinkedIn email field found successfully.");

        return this;
    }

    public HomePage login(String user, String pass) {

        System.out.println("Starting login process...");

        WebElement email = findVisibleInput(usernameField);
        email.clear();
        email.sendKeys(user);

        System.out.println("Email entered successfully.");

        WebElement password = findVisibleInput(passwordField);
        password.clear();
        password.sendKeys(pass);

        System.out.println("Password entered successfully.");

        WebElement signIn = findVisibleButton(signInButton);

        signIn.click();

        System.out.println("Sign in button clicked.");

        return new HomePage(driver);
    }

    public boolean isErrorDisplayed() {
        try {
            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(errorMessage)
            ).isDisplayed();

        } catch (TimeoutException e) {
            return false;
        }
    }

    public String getErrorText() {
        return waitVisible(errorMessage).getText();
    }

    /*
     * Wait for LinkedIn feed.
     * If security verification is required,
     * complete it manually.
     */
    public HomePage waitForManualVerificationIfNeeded() {

        try {

            new WebDriverWait(driver, Duration.ofSeconds(15))
                    .until(ExpectedConditions.urlContains("/feed"));

        } catch (TimeoutException e) {

            System.out.println(
                    "LinkedIn feed not detected yet."
            );

            System.out.println(
                    "If security verification or 2FA appears, complete it manually."
            );

            new WebDriverWait(driver, Duration.ofMinutes(2))
                    .until(ExpectedConditions.urlContains("/feed"));
        }

        return new HomePage(driver);
    }
}
