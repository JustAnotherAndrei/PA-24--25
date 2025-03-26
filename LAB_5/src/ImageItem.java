import java.util.List;
import java.time.LocalDate;

public record ImageItem(String name, LocalDate date, List<String> tags, String filePath) {
    @Override
    public String toString() {
        return "Image: " + name + " | Date: " + date + " | Tags: " + tags + " | Path: " + filePath;
    }
}
