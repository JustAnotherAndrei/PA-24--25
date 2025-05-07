package model;

public class City {
    private int id;
    private String name;
    private String country;
    private boolean capital;
    private double latitude;
    private double longitude;

    public City(int id, String name, String country, boolean capital, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.capital = capital;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters and setters omitted for brevity
}
