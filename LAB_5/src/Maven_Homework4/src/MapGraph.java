// MapGraph.java
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import java.util.List;
import java.util.ArrayList;

public class MapGraph {
    // Create a directed graph where vertices are Location objects
    // and edges (DefaultWeightedEdge) carry the travel time as weight.
    private Graph<Location, DefaultWeightedEdge> graph;
    private List<Location> locations;
    private Location startLocation;

    public MapGraph() {
        graph = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
        locations = new ArrayList<>();
    }

    // Add a location (vertex) to the graph.
    public void addLocation(Location location) {
        graph.addVertex(location);
        locations.add(location);
    }

    // Add a directed edge from source to target with a given time as weight.
    public void addEdge(Location source, Location target, double time) {
        DefaultWeightedEdge edge = graph.addEdge(source, target);
        if (edge != null) {
            graph.setEdgeWeight(edge, time);
        }
    }

    // Set the start location from which fastest routes will be computed.
    public void setStartLocation(Location location) {
        startLocation = location;
    }

    // Dijkstra's algorithm in order to compute the fastest travel time from start location to target.
    public double getFastestTime(Location target) {
        if(startLocation == null) {
            throw new IllegalStateException("Start location not set");
        }
        DijkstraShortestPath<Location, DefaultWeightedEdge> dijkstra =
                new DijkstraShortestPath<>(graph);
        return dijkstra.getPathWeight(startLocation, target);
    }

    // Graph getter
    public Graph<Location, DefaultWeightedEdge> getGraph() {
        return graph;
    }

    // Getter for the list of all locations.
    public List<Location> getLocations() {
        return locations;
    }

    // Getter for the start location.
    public Location getStartLocation() {
        return startLocation;
    }
}

