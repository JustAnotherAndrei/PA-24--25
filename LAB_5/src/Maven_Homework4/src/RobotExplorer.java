// RobotExplorer.java
import com.github.javafaker.Faker;
import java.util.*;
import java.util.stream.Collectors;

public class RobotExplorer {
    public static void main(String[] args) {
        // Create an instance of Faker to generate random fake names.
        Faker faker = new Faker();

        // Create a MapGraph instance to hold our locations and routes.
        MapGraph mapGraph = new MapGraph();

        // Create locations with random names and assign each a type.
        // For demonstration purposes, we create 9 locations (3 per type).
        Location loc1 = new Location(faker.address().city(), Location.LocationType.FRIENDLY);
        Location loc2 = new Location(faker.address().city(), Location.LocationType.FRIENDLY);
        Location loc3 = new Location(faker.address().city(), Location.LocationType.FRIENDLY);
        Location loc4 = new Location(faker.address().city(), Location.LocationType.NEUTRAL);
        Location loc5 = new Location(faker.address().city(), Location.LocationType.NEUTRAL);
        Location loc6 = new Location(faker.address().city(), Location.LocationType.NEUTRAL);
        Location loc7 = new Location(faker.address().city(), Location.LocationType.ENEMY);
        Location loc8 = new Location(faker.address().city(), Location.LocationType.ENEMY);
        Location loc9 = new Location(faker.address().city(), Location.LocationType.ENEMY);

        // Add all locations to the map graph.
        Arrays.asList(loc1, loc2, loc3, loc4, loc5, loc6, loc7, loc8, loc9)
                .forEach(mapGraph::addLocation);

        // Set the starting location (for example, the first created location).
        mapGraph.setStartLocation(loc1);

        // Create random direct connections (edges) between locations.
        // For each pair of distinct locations, randomly decide (50% chance) to create an edge.
        // The travel time is chosen randomly between 1 and 10.
        Random rand = new Random();
        List<Location> locations = mapGraph.getLocations();
        for (Location source : locations) {
            for (Location target : locations) {
                if (!source.equals(target)) {
                    if(rand.nextBoolean()){
                        double time = 1 + rand.nextInt(10);
                        mapGraph.addEdge(source, target, time);
                    }
                }
            }
        }

        // Compute the fastest travel times from the start location to every other location.
        // We use the DijkstraShortestPath method defined in MapGraph.
        Map<Location, Double> fastestTimes = new HashMap<>();
        for(Location location : locations) {
            double time = mapGraph.getFastestTime(location);
            fastestTimes.put(location, time);
        }

        // Use Java Stream API to group locations by their type.
        Map<Location.LocationType, List<Location>> locationsByType =
                locations.stream()
                        .collect(Collectors.groupingBy(Location::getType));

        // Display the fastest times for each type of location in the following order:
        // FRIENDLY, NEUTRAL, then ENEMY.
        System.out.println("Fastest times from start location " + mapGraph.getStartLocation() + ":");
        for (Location.LocationType type : Arrays.asList(Location.LocationType.FRIENDLY, Location.LocationType.NEUTRAL, Location.LocationType.ENEMY)) {
            List<Location> locs = locationsByType.getOrDefault(type, Collections.emptyList());
            System.out.println("\n" + type + " locations:");
            locs.forEach(loc -> {
                double time = fastestTimes.getOrDefault(loc, Double.POSITIVE_INFINITY);
                System.out.println(loc + " -> Fastest time: " + time);
            });
        }
    }
}
