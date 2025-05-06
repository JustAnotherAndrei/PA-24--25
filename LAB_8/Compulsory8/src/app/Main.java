package app;

import dao.ContinentDAO;
import dao.CountryDAO;
import model.Continent;
import model.Country;

public class Main {
    public static void main(String[] args) {
        try {
            ContinentDAO continentDAO = new ContinentDAO();
            CountryDAO countryDAO = new CountryDAO();

            // Add a continent
            Continent asia = new Continent("Asia");
            continentDAO.addContinent(asia);

            // Retrieve continent
            Continent retrievedContinent = continentDAO.findByName("Asia");
            System.out.println("Retrieved Continent: " + retrievedContinent);

            // Add a country
            Country japan = new Country("Japan", "JP", retrievedContinent.getId());
            countryDAO.addCountry(japan);

            // Retrieve country
            Country retrievedCountry = countryDAO.findByName("Japan");
            System.out.println("Retrieved Country: " + retrievedCountry);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
