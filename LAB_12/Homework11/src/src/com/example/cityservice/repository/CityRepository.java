// File: city-service/src/com/example/cityservice/repository/CityRepository.java
package com.example.cityservice.repository;

import com.example.cityservice.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for City entities.
 * Inherits CRUD operations: findAll(), findById(), save(), deleteById(), etc.
 */
@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    // No additional methods needed for basic CRUD.
}
