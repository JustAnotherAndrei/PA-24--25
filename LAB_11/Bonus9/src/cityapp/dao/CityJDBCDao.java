package cityapp.dao;

import cityapp.entity.City;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CityJDBCDao implements CityDAO {
    private final Connection conn;

    public CityJDBCDao() {
        try {
            conn = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS cities (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR, country VARCHAR, population INT)");
        } catch (SQLException e) {
            throw new RuntimeException("JDBC Init failed", e);
        }
    }

    @Override
    public void save(City city) {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO cities(name, country, population) VALUES (?, ?, ?)");
            ps.setString(1, city.getName());
            ps.setString(2, city.getCountry());
            ps.setInt(3, city.getPopulation());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<City> findAll() {
        List<City> list = new ArrayList<>();
        try {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM cities");
            while (rs.next()) {
                list.add(new City(rs.getString("name"), rs.getString("country"), rs.getInt("population")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
