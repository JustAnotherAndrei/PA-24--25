import java.util.*;
import java.util.stream.Collectors;

public class Bonus4 {
    public static void main(String[] args) {
        int numLocations = 2137;
        int numTestRoutes = 456;

        // Generate random locations
        RandomProblemGenerator problemGenerator = new RandomProblemGenerator();
        List<Location> locations = problemGenerator.generateLocations(numLocations);

        // Initialize the RouteFinder and list to store computed routes
        RouteFinder routeFinder = new RouteFinder();
        List<Route> routes = new ArrayList<>();
        Random random = new Random();

        // Generate random pairs and compute routes
        for (int i = 0; i < numTestRoutes; i++) {
            Location source = locations.get(random.nextInt(locations.size()));
            Location destination = locations.get(random.nextInt(locations.size()));
            // Ensure source and destination are not the same
            while (destination.equals(source)) {
                destination = locations.get(random.nextInt(locations.size()));
            }
            Route route = routeFinder.findSafestRoute(locations, source, destination);
            routes.add(route);
        }


        // 1. Average route length (number of locations in the route)
        double averageRouteLength = routes.stream()
                .mapToInt(r -> r.getPath().size())
                .average()
                .orElse(0.0);

        // 2. Average count of each LocationType across all routes
        Map<LocationType, Double> averageTypeCounts = new EnumMap<>(LocationType.class);
        for (LocationType type : LocationType.values()) {
            double avg = routes.stream()
                    .mapToLong(r -> r.getTypeCounts().getOrDefault(type, 0L))
                    .average()
                    .orElse(0.0);
            averageTypeCounts.put(type, avg);
        }

        // Print aggregate results
        System.out.println("Total test routes computed: " + routes.size());
        System.out.println("Average route length: " + averageRouteLength);
        System.out.println("Average count of location types per route:");
        averageTypeCounts.forEach((type, avg) ->
                System.out.println(type + ": " + avg));
    }
}
