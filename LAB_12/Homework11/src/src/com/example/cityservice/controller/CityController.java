// File: city-service/src/com/example/cityservice/controller/CityController.java
package com.example.cityservice.controller;

import com.example.cityservice.model.City;
import com.example.cityservice.repository.CityRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

/**
 * REST controller exposing CRUD endpoints for City under /api/cities.
 */
@RestController
@RequestMapping("/api/cities")
@Tag(name = "City Controller", description = "APIs for managing cities")
public class CityController {

    private final CityRepository cityRepository;

    public CityController(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    /**
     * GET /api/cities
     * Returns a list of all cities.
     */
    @GetMapping
    @Operation(summary = "Get all cities", description = "Returns a list of all cities")
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    /**
     * POST /api/cities
     * Creates a new city. Expects JSON: { "name": "SomeCity" }.
     * Returns HTTP 201 with the created City (including generated id).
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new city", description = "Adds a new city with the provided name")
    public City createCity(@Valid @RequestBody City city) {
        return cityRepository.save(city);
    }

    /**
     * PUT /api/cities/{id}
     * Updates the name of the city with the given ID.
     * If not found, returns HTTP 404.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update a city", description = "Modifies the name of the city with the given ID")
    public City updateCity(@PathVariable Long id,
                           @Valid @RequestBody City cityRequest) {
        return cityRepository.findById(id)
                .map(existingCity -> {
                    existingCity.setName(cityRequest.getName());
                    return cityRepository.save(existingCity);
                })
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "City with ID " + id + " not found"
                ));
    }

    /**
     * DELETE /api/cities/{id}
     * Deletes the city with the given ID. Returns HTTP 204 if successful.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a city", description = "Deletes the city with the given ID")
    public void deleteCity(@PathVariable Long id) {
        if (!cityRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "City with ID " + id + " not found"
            );
        }
        cityRepository.deleteById(id);
    }
}
