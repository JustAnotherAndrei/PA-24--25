public class Location {
    private final int id;
    private final LocationType type;

    public Location(int id, LocationType type) {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public LocationType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Location{" + "id=" + id + ", type=" + type + '}';
    }
}

