import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomProblemGenerator {
    private final Random random = new Random();

    public List<Location> generateLocations(int numLocations) {
        List<Location> locations = new ArrayList<>();
        for (int i = 0; i < numLocations; i++) {
            // Randomly assign one of the available types
            LocationType type = LocationType.values()[random.nextInt(LocationType.values().length)];
            locations.add(new Location(i, type));
        }
        return locations;
    }
}
