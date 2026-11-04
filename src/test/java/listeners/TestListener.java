
package listeners;

import base.BaseTest;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.ScreenshotUtil;

public class TestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {

        // Get the current test class instance
        Object instance = result.getInstance();

        // Check whether the test extends BaseTest
        if (instance instanceof BaseTest base && base.getDriver() != null) {

            // Capture screenshot when test fails
            String path = ScreenshotUtil.capture(
                    base.getDriver(),
                    result.getName()
            );

            System.out.println("Screenshot saved: " + path);
        }
    }
}
