package importdata;

import model.City;
import service.CityService;

import java.io.BufferedReader;
import java.io.FileReader;

public class CityImporter {

    public void importFromCSV(String path) {
        CityService service = new CityService();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Expected CSV format: name,country,capital,lat,lng
                String[] tokens = line.split(",");
                if (tokens.length != 5) continue;
                String name = tokens[0];
                String country = tokens[1];
                boolean capital = Boolean.parseBoolean(tokens[2]);
                double lat = Double.parseDouble(tokens[3]);
                double lng = Double.parseDouble(tokens[4]);

                City city = new City(0, name, country, capital, lat, lng);
                service.insertCity(city);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

