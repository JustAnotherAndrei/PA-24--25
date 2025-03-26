// Location.java
public class Location {
    // Enum representing the types of locations.
    public enum LocationType {
        FRIENDLY, NEUTRAL, ENEMY;
    }

    private String name;
    private LocationType type;

    // Constructor to initialize the location with a name and a type.
    public Location(String name, LocationType type) {
        this.name = name;
        this.type = type;
    }

    // Getter for the name.
    public String getName() {
        return name;
    }

    // Getter for the location type.
    public LocationType getType() {
        return type;
    }

    // Overridden toString method for easy display.
    @Override
    public String toString() {
        return name + " (" + type + ")";
    }

    // Override hashCode and equals for correct behavior in collections.
    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj instanceof Location) {
            Location other = (Location)obj;
            return this.name.equals(other.name);
        }
        return false;
    }
}

