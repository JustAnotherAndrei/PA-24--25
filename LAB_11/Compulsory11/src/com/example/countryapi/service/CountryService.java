package com.example.countryapi.service;

import com.example.countryapi.model.Country;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

@Service
public class CountryService {
    public List<Country> getAllCountries() {
        return Arrays.asList(
                new Country("United States","US"),
                new Country("Germany","DE"),
                new Country("France","FR"),
                new Country("Japan","JP")
        );
    }
}
