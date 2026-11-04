
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {

    private final By searchBox = By.cssSelector(
            "input.search-global-typeahead__input, " +
                    "input[aria-label='Search'], " +
                    "input[placeholder='Search'], " +
                    "[role='search'] input"
    );

    public HomePage(WebDriver driver) {
        super(driver);
    }

    // Check whether user is logged in
    public boolean isLoggedIn() {

        wait.until(
                ExpectedConditions.urlContains("/feed")
        );

        return isDisplayed(searchBox);
    }

    // Search for a person or keyword
    public SearchResultsPage search(String term) {

        click(searchBox);

        WebElement box = waitVisible(searchBox);

        box.clear();

        box.sendKeys(term);

        box.sendKeys(Keys.ENTER);

        return new SearchResultsPage(driver);
    }
}
