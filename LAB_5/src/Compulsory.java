import java.time.LocalDate;
import java.util.Arrays;

public class Compulsory {
    public static void main(String[] args) {
        ImageRepository repository = new ImageRepository();

        ImageItem img1 = new ImageItem("Budapest", LocalDate.of(2025, 3, 25), Arrays.asList("Bazdmeg", "Szerettem Budapestben jarni"), "C:\\Users\\Andrei\\OneDrive\\Pictures\\Budapest.jpg");
        ImageItem img2 = new ImageItem("Warsaw", LocalDate.of(2025, 3, 25), Arrays.asList("Czesc", "Bardzo podobalo mi sie zwiedzac Warszawe"), "C:\\Users\\Andrei\\OneDrive\\Pictures\\Warsaw.jpg");

        repository.addImage(img1);
        repository.addImage(img2);

        System.out.println("\nRepository Images:");
        for (ImageItem img : repository.getImages()) {
            System.out.println(img);
        }

        System.out.println("\nOpening image...");
        DesktopUtil.displayImage(img1.filePath());
    }
}

