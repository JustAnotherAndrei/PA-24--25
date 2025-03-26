import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class DesktopUtil {
    public static void displayImage(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("File not found: " + filePath);
            return;
        }

        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().open(file);
            } catch (IOException e) {
                System.out.println("Cannot open file: " + e.getMessage());
            }
        } else {
            System.out.println("Desktop not supported on this system.");
        }
    }
}

