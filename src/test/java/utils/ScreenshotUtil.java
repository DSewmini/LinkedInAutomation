
package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotUtil {

    public static String capture(WebDriver driver, String name) {

        try {

            // Capture screenshot
            File src = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.FILE);

            // Generate current date and time
            String time = LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")
            );

            // Screenshot destination
            Path dest = Path.of(
                    "screenshots",
                    name + "_" + time + ".png"
            );

            // Create screenshots folder
            Files.createDirectories(dest.getParent());

            // Save screenshot
            Files.copy(
                    src.toPath(),
                    dest,
                    StandardCopyOption.REPLACE_EXISTING
            );

            return dest.toString();

        } catch (IOException e) {

            System.err.println(
                    "Screenshot failed: " + e.getMessage()
            );

            return null;
        }
    }
}
