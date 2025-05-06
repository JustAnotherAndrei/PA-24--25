package model;

public class Continent {
    private int id;
    private String name;

    public Continent(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Continent(String name) {
        this.name = name;
    }

    public int getId() { return id; }
    public String getName() { return name; }

    @Override
    public String toString() {
        return "Continent{" + "id=" + id + ", name='" + name + '\'' + '}';
    }
}
