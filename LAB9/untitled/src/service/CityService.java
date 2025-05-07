package service;

import db.DatabaseManager;
import model.City;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CityService {

    public void createCityTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS cities (
                id SERIAL PRIMARY KEY,
                name VARCHAR(100),
                country VARCHAR(100),
                capital BOOLEAN,
                latitude DOUBLE PRECISION,
                longitude DOUBLE PRECISION
            )
            """;
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertCity(City city) {
        String sql = "INSERT INTO cities (name, country, capital, latitude, longitude) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, city.getName());
            stmt.setString(2, city.getCountry());
            stmt.setBoolean(3, city.isCapital());
            stmt.setDouble(4, city.getLatitude());
            stmt.setDouble(5, city.getLongitude());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<City> getAllCities() {
        List<City> cities = new ArrayList<>();
        String sql = "SELECT * FROM cities";
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                cities.add(new City(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("country"),
                        rs.getBoolean("capital"),
                        rs.getDouble("latitude"),
                        rs.getDouble("longitude")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cities;
    }
}

