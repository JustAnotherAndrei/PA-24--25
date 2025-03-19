package Proiect_fain.Proiect_fain.src.main.java.org.example;

class Location implements Comparable<Location> {
    private final String name;
    private final Type type;

    public Location(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    @Override
    public int compareTo(Location other) {
        return this.name.compareTo(other.name);
    }

    @Override
    public String toString() {
        return "Location{" +
                "name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}

enum Type {
    FRIENDLY,
    NEUTRAL,
    ENEMY
}
