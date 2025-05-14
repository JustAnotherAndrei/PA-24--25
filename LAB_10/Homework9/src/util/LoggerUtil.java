package util;

import java.io.IOException;
import java.util.logging.*;

public class LoggerUtil {
    public static Logger createLogger(String name) {
        Logger logger = Logger.getLogger(name);
        try {
            FileHandler fh = new FileHandler("app.log", true);
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
        } catch (IOException e) {
            logger.severe("Failed to create file handler: " + e.getMessage());
        }
        logger.setUseParentHandlers(true);
        return logger;
    }
}

