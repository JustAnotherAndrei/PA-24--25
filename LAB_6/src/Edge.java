public class Edge {
    private final Location source;
    private final Location destination;
    private final int weight;  // Represents "risk score" (lower is safer)

    public Edge(Location source, Location destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    public Location getSource() {
        return source;
    }

    public Location getDestination() {
        return destination;
    }

    public int getWeight() {
        return weight;
    }
}
