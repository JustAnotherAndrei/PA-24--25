package com.example.app.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "persons")
@NamedQuery(
        name = "Person.findByName",
        query = "SELECT p FROM Person p WHERE p.name LIKE :name"
)
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    public Person() {
    }

    public Person(String name) {
        this.name = name;
    }

    // Getters and setters
    public Long getId() { return id; }
    public String getName() { return name; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return "Person{id=" + id + ", name='" + name + "'}";
    }
}
