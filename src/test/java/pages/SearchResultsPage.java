
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SearchResultsPage extends BasePage {

    private final By firstPersonLink = By.cssSelector(
            "main a[href*='/in/']"
    );

    private final By peopleFilter = By.xpath(
            "//button[normalize-space()='People'] | " +
                    "//a[normalize-space()='People']"
    );

    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    // Check whether the search results page is displayed
    public boolean isResultsPageDisplayed() {
        wait.until(
                ExpectedConditions.urlContains("/search/results")
        );
        return true;
    }

    // Check whether at least one person result is visible

    public boolean hasVisibleResult() {

        try {
            return wait.until(driver ->
                    driver.findElements(
                                    By.cssSelector("a[href*='/in/']")
                            )
                            .stream()
                            .anyMatch(WebElement::isDisplayed)
            );

        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

    // Display information about the People filter
    public void debugPeopleFilter() {

        System.out.println("Checking People filter...");

        java.util.List<WebElement> filters =
                driver.findElements(peopleFilter);

        System.out.println("People filter matches: " + filters.size());

        for (WebElement filter : filters) {
            System.out.println(
                    "Visible: " + filter.isDisplayed()
                            + " | Text: " + filter.getText()
            );
        }
    }

    // Get the URL and click the same first visible profile link
    public String clickFirstResultAndGetHref() {

        WebElement first = wait.until(driver ->
                driver.findElements(firstPersonLink)
                        .stream()
                        .filter(WebElement::isDisplayed)
                        .filter(element ->
                                element.getAttribute("href") != null
                                        && element.getAttribute("href").contains("/in/")
                        )
                        .findFirst()
                        .orElse(null)
        );

        String href = first.getAttribute("href");

        System.out.println("Selected profile URL: " + href);

        wait.until(
                ExpectedConditions.elementToBeClickable(first)
        );

        first.click();

        return href;
    }


    public void debugProfileLinks() {

        System.out.println("\n--- VISIBLE PROFILE LINKS ---");

        java.util.List<WebElement> links =
                driver.findElements(
                        By.cssSelector("a[href*='/in/']")
                );

        int count = 0;

        for (WebElement link : links) {

            if (link.isDisplayed()) {

                count++;

                System.out.println("\nLink " + count);

                System.out.println("Text: " + link.getText());

                System.out.println(
                        "Aria label: " + link.getAttribute("aria-label")
                );

                System.out.println(
                        "URL: " + link.getAttribute("href")
                );

                System.out.println(
                        "Parent text: " +
                                link.findElement(By.xpath("..")).getText()
                );
            }
        }

        System.out.println("\nTotal visible profile links: " + count);
    }
}
