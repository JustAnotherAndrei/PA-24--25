import java.util.*;

public class Graph {
    private final Map<Location, List<Edge>> adjacencyList = new HashMap<>();

    public void addLocation(Location location) {
        adjacencyList.putIfAbsent(location, new ArrayList<>());
    }

    public void addEdge(Location from, Location to, int weight) {
        adjacencyList.get(from).add(new Edge(from, to, weight));
        adjacencyList.get(to).add(new Edge(to, from, weight));  // Bidirectional
    }

    public List<Edge> getEdges(Location location) {
        return adjacencyList.getOrDefault(location, Collections.emptyList());
    }

    public Set<Location> getLocations() {
        return adjacencyList.keySet();
    }
}
