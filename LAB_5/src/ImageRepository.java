import java.util.ArrayList;
import java.util.List;

public class ImageRepository {
    private final List<ImageItem> images = new ArrayList<>();

    public void addImage(ImageItem image) {
        images.add(image);
        System.out.println("Added: " + image);
    }

    public List<ImageItem> getImages() {
        return new ArrayList<>(images); // Return a copy to protect internal list
    }
}
