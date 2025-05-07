package com.example.app;

import com.example.app.entity.Person;
import com.example.app.repository.PersonRepository;
import com.example.app.util.JPAUtil;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        PersonRepository repo = new PersonRepository();

        // Create new persons
        repo.create(new Person("Alice"));
        repo.create(new Person("Bob"));
        repo.create(new Person("Alicia"));

        // Find by ID
        Person p = repo.findById(1L);
        System.out.println("Found by ID: " + p);

        // Find by Name
        List<Person> persons = repo.findByName("Ali%");
        System.out.println("Found by name 'Ali%':");
        for (Person person : persons) {
            System.out.println(person);
        }

        // Shutdown JPA
        JPAUtil.shutdown();
    }
}
