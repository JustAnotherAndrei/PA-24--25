package cityapp.util;

import cityapp.dao.CityDAO;
import cityapp.entity.City;

public class DatabaseInitializer {
    public static void init(CityDAO dao) {
        dao.save(new City("Amsterdam", "Netherlands", 900000));
        dao.save(new City("Athens", "Greece", 700000));
        dao.save(new City("Berlin", "Germany", 3600000));
        dao.save(new City("Brussels", "Belgium", 1200000));
        dao.save(new City("Copenhagen", "Denmark", 800000));
        dao.save(new City("Chicago", "USA", 2700000));
        dao.save(new City("Calgary", "Canada", 1300000));
    }
}
