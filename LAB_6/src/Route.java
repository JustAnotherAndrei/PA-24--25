import java.util.*;
import java.util.stream.Collectors;

public class Route {
    private final Location source;
    private final Location destination;
    private final List<Location> path;
    private final Map<LocationType, Long> typeCounts;

    public Route(Location source, Location destination, List<Location> path) {
        this.source = source;
        this.destination = destination;
        this.path = path;
        this.typeCounts = calculateTypeCounts();
    }

    // Compute the count of locations for each type along the route
    private Map<LocationType, Long> calculateTypeCounts() {
        return path.stream()
                .collect(Collectors.groupingBy(Location::getType, Collectors.counting()));
    }

    public Location getSource() {
        return source;
    }

    public Location getDestination() {
        return destination;
    }

    public List<Location> getPath() {
        return path;
    }

    public Map<LocationType, Long> getTypeCounts() {
        return typeCounts;
    }

    @Override
    public String toString() {
        return "Route{" +
                "source=" + source +
                ", destination=" + destination +
                ", path=" + path +
                ", typeCounts=" + typeCounts +
                '}';
    }
}
