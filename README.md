# LinkedIn UI Test Suite (Selenium + TestNG)

Automated UI tests for three LinkedIn flows: **login**, **search**, and **opening a search result**.


> **Ethics notice:** Use only my own (preferably a dedicated test) LinkedIn account.
> If LinkedIn shows a CAPTCHA or verification step, do **not** bypass it – the test pauses
> (up to 2 minutes) so you can complete it manually.

## Tech stack

| Item | Version |
|------|---------|
| Java | 17+ |
| Selenium WebDriver | 4.27.0 (Selenium Manager downloads ChromeDriver automatically) |
| TestNG | 7.10.2 |
| Build tool | Maven |
| Browser | Google Chrome |

## Project structure

```
linkedin-tests/
├── pom.xml
├── testng.xml                      # Suite definition + listener
├── .gitignore
└── src/test/
    ├── java/
    │   ├── base/BaseTest.java          # Starts/stops browser, loginAsValidUser()
    │   ├── pages/                      # Page Object Model
    │   │   ├── BasePage.java           # Explicit-wait helpers (click, type, waitVisible)
    │   │   ├── LoginPage.java
    │   │   ├── HomePage.java
    │   │   └── SearchResultsPage.java
    │   ├── tests/
    │   │   ├── LoginTest.java          # TC01, TC02
    │   │   ├── SearchTest.java         # TC03
    │   │   └── SearchResultTest.java   # TC04
    │   ├── listeners/TestListener.java # Screenshot on failure
    │   └── utils/
    │       ├── Config.java             # Reads credentials/config
    │       └── ScreenshotUtil.java
    └── resources/
        └── config.properties           # YOUR credentials (NOT committed)
```

## Setup

1. **Install** JDK 17+, Maven, and Google Chrome.
2. **Clone** the repository:
   ```bash
   git clone <https://github.com/DSewmini/LinkedInAutomation.git
   cd linkedin-tests
   ```
3. **Configure credentials.** 
   ```bash
   cp config_properties.example src/test/resources/config.properties
   ```
   ```properties
   linkedin.username=YOUR_LINKEDIN_EMAIL
   linkedin.password=YOUR_LINKEDIN_PASSWORD
   search.term=Satya Nadella
   ```
   `config.properties` is listed in `.gitignore` – **never commit real credentials.**

   Credentials can alternatively be supplied (highest priority first) as:
    - JVM properties: `-Dlinkedin.username=... -Dlinkedin.password=...`
    - Environment variables: `LINKEDIN_USERNAME`, `LINKEDIN_PASSWORD`, `SEARCH_TERM`

## Running the tests

Run the full suite (uses `testng.xml`):
```bash
mvn clean test
```

Run a single class:
```bash
mvn test -Dtest=LoginTest
```

Pass credentials without a file:
```bash
mvn test -Dlinkedin.username=me@example.com -Dlinkedin.password=secret
```

From an IDE (IntelliJ/Eclipse): right-click `testng.xml` → *Run*.

## Test cases

| ID | Class | Description |
|----|-------|-------------|
| TC01 | `LoginTest` | Valid login reaches the feed (`/feed` URL + search box visible) |
| TC02 | `LoginTest` | Invalid password shows an error message |
| TC03 | `SearchTest` | Search shows results page (`/search/results` URL + a visible profile result) |
| TC04 | `SearchResultTest` | Clicking the first result opens a profile (`/in/...`) matching the clicked link |

## Waits

The suite uses **explicit waits** (`WebDriverWait` + `ExpectedConditions` / custom lambdas) in `BasePage`
and the page classes – no `Thread.sleep()` and no implicit waits.

## Handling CAPTCHA / 2FA

`LoginPage.waitForManualVerificationIfNeeded()` waits 15 s for the feed. If it does not appear,
it prints a message and waits up to 2 more minutes so you can **complete the verification manually
in the browser window**. The suite never attempts to bypass LinkedIn security.

## Screenshots

On any test failure, `TestListener` saves a PNG to the `screenshots/` folder
(`<testName>_<yyyyMMdd_HHmmss>.png`).

## Known limitations

- LinkedIn changes its HTML often; locators may need updating.
- Repeated automated logins can trigger CAPTCHAs or temporary restrictions – run sparingly.
- Tests depend on a live third-party site and a real account, so results can vary.
- Tests share one account and run sequentially; do not run in parallel.

## .gitignore (recommended)

```
src/test/resources/config.properties
screenshots/
target/
.idea/
*.iml
```