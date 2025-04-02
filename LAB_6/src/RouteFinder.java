import java.util.*;
import java.util.stream.Collectors;

public class RouteFinder {
    private final Random random = new Random();

    /**
     * For simplicity, this method simulates the computation of the safest route by generating a random route.
     * In a real-world application, you might use a pathfinding algorithm (like Dijkstra's algorithm) with risk factors.
     *
     * @param locations  the list of all available locations
     * @param source     the starting location
     * @param destination the ending location
     * @return a Route instance representing the computed path
     */
    public Route findSafestRoute(List<Location> locations, Location source, Location destination) {
        // Generate a random route length between 2 and 11 (including source and destination)
        int pathLength = random.nextInt(10) + 2;
        List<Location> path = new ArrayList<>();
        path.add(source);

        // Get candidates for intermediate stops (exclude source and destination)
        List<Location> candidates = new ArrayList<>(locations);
        candidates.remove(source);
        candidates.remove(destination);
        Collections.shuffle(candidates, random);

        // Select a number of intermediate stops, ensuring we don't exceed available candidates
        int stops = Math.min(pathLength - 2, candidates.size());
        path.addAll(candidates.subList(0, stops));
        path.add(destination);

        // Return the route with the computed intermediate stops and type counts
        return new Route(source, destination, path);
    }
}
