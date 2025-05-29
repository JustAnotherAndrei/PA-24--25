package cityapp.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DatabaseConfig {
    private final Properties props = new Properties();

    public DatabaseConfig(String filename) throws IOException {
        try (FileInputStream in = new FileInputStream(filename)) {
            props.load(in);
        }
    }

    public String getAccessType() {
        return props.getProperty("data.access.type", "jdbc");
    }
}
