package dao;

import db.DatabaseConnection;
import model.Country;

import java.sql.*;

public class CountryDAO {

    public void addCountry(Country country) throws SQLException {
        String sql = "INSERT INTO countries(name, code, continent_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, country.getName());
            stmt.setString(2, country.getCode());
            stmt.setInt(3, country.getContinentId());
            stmt.executeUpdate();
        }
    }

    public Country findByName(String name) throws SQLException {
        String sql = "SELECT * FROM countries WHERE name = ?";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Country(rs.getInt("id"), rs.getString("name"),
                        rs.getString("code"), rs.getInt("continent_id"));
            }
        }
        return null;
    }

    public Country findById(int id) throws SQLException {
        String sql = "SELECT * FROM countries WHERE id = ?";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Country(rs.getInt("id"), rs.getString("name"),
                        rs.getString("code"), rs.getInt("continent_id"));
            }
        }
        return null;
    }
}
