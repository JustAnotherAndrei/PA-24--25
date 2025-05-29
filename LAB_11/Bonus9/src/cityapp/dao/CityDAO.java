package cityapp.dao;

import cityapp.entity.City;

import java.util.List;

public interface CityDAO {
    void save(City city);
    List<City> findAll();
}
