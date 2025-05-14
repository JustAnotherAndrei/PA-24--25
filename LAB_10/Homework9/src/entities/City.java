package entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cities")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private double x;
    private double y;

    @ManyToMany
    @JoinTable(name = "sisters",
            joinColumns = @JoinColumn(name = "city1_id"),
            inverseJoinColumns = @JoinColumn(name = "city2_id"))
    private Set<City> sisters = new HashSet<>();

    public City() {
    }

    public City(String name, double x, double y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    // Implemented getName and getSisters
    public String getName() {
        return this.name;
    }

    public Set<City> getSisters() {
        return this.sisters;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setSisters(Set<City> sisters) {
        this.sisters = sisters;
    }

    // Convenience methods for bidirectional relationship
    public void addSister(City sister) {
        this.sisters.add(sister);
        sister.getSisters().add(this);
    }

    public void removeSister(City sister) {
        this.sisters.remove(sister);
        sister.getSisters().remove(this);
    }
}
