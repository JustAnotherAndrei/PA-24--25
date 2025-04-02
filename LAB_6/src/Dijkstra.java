import java.util.*;

public class Dijkstra {
    public static List<Location> findSafestRoute(Graph graph, Location source, Location destination) {
        Map<Location, Integer> riskScores = new HashMap<>();
        Map<Location, Location> previousNode = new HashMap<>();
        PriorityQueue<Location> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(riskScores::get));

        for (Location location : graph.getLocations()) {
            riskScores.put(location, Integer.MAX_VALUE);  // Start with "infinity" risk
        }
        riskScores.put(source, 0);
        priorityQueue.add(source);

        while (!priorityQueue.isEmpty()) {
            Location current = priorityQueue.poll();

            if (current.equals(destination)) {
                return reconstructPath(previousNode, source, destination);
            }

            for (Edge edge : graph.getEdges(current)) {
                Location neighbor = edge.getDestination();
                int newRisk = riskScores.get(current) + edge.getWeight();

                if (newRisk < riskScores.get(neighbor)) {
                    riskScores.put(neighbor, newRisk);
                    previousNode.put(neighbor, current);
                    priorityQueue.add(neighbor);
                }
            }
        }

        return Collections.emptyList();  // No path found
    }

    private static List<Location> reconstructPath(Map<Location, Location> previousNode, Location source, Location destination) {
        List<Location> path = new LinkedList<>();
        for (Location at = destination; at != null; at = previousNode.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }
}
