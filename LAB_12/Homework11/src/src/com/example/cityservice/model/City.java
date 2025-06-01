// File: city-service/src/com/example/cityservice/model/City.java
package com.example.cityservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

/**
 * JPA entity representing a City.
 */
@Entity
@Table(name = "cities")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "City name must not be blank")
    private String name;

    public City() {
        // Default constructor required by JPA
    }

    public City(String name) {
        this.name = name;
    }

    // Getter for id (no setter; generated automatically)
    public Long getId() {
        return id;
    }

    // Getter/setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // toString (optional, but helpful for debugging)
    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
