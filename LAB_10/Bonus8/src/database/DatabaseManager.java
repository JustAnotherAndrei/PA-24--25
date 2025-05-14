package database;

import java.sql.*;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:cities.db"; // SQLite for simplicity
    private Connection connection;

    public void connect() throws SQLException {
        connection = DriverManager.getConnection(DB_URL);
    }

    public void createTables() throws SQLException {
        String createCity = "CREATE TABLE IF NOT EXISTS city (id INTEGER PRIMARY KEY, name TEXT, x REAL, y REAL)";
        String createRelation = "CREATE TABLE IF NOT EXISTS sister (city1 INTEGER, city2 INTEGER, FOREIGN KEY(city1) REFERENCES city(id), FOREIGN KEY(city2) REFERENCES city(id))";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createCity);
            stmt.execute(createRelation);
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
