package dao;

import db.DatabaseConnection;
import model.Continent;

import java.sql.*;

public class ContinentDAO {

    public void addContinent(Continent continent) throws SQLException {
        String sql = "INSERT INTO continents(name) VALUES (?)";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, continent.getName());
            stmt.executeUpdate();
        }
    }

    public Continent findByName(String name) throws SQLException {
        String sql = "SELECT * FROM continents WHERE name = ?";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Continent(rs.getInt("id"), rs.getString("name"));
            }
        }
        return null;
    }

    public Continent findById(int id) throws SQLException {
        String sql = "SELECT * FROM continents WHERE id = ?";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Continent(rs.getInt("id"), rs.getString("name"));
            }
        }
        return null;
    }
}
