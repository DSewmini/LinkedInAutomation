
package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    private static final Properties props = new Properties();

    // Load config.properties file
    static {
        try (InputStream in = Config.class.getClassLoader()
                .getResourceAsStream("config.properties")) {

            if (in != null) {
                props.load(in);
            } else {
                System.out.println("config.properties file not found.");
            }

        } catch (IOException e) {
            throw new RuntimeException("Could not read config.properties", e);
        }
    }

    // Get configuration values
    public static String get(String key) {

        String value = System.getProperty(key);

        if (value == null) {
            value = System.getenv(
                    key.toUpperCase().replace('.', '_')
            );
        }

        if (value == null) {
            value = props.getProperty(key);
        }

        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    "Missing config value: " + key
            );
        }

        return value.trim();
    }
}
