package util;

import database.*;

import java.sql.*;
import java.util.*;

public class DataGenerator {
    private static final int CITY_COUNT = 10000;
    private static final double SISTER_PROB = 0.0005;

    public static void generate(DatabaseManager db) throws SQLException {
        Random rand = new Random();
        Connection conn = db.getConnection();

        try (PreparedStatement cityStmt = conn.prepareStatement("INSERT INTO city (id, name, x, y) VALUES (?, ?, ?, ?)");
             PreparedStatement sisterStmt = conn.prepareStatement("INSERT INTO sister (city1, city2) VALUES (?, ?)")) {

            for (int i = 0; i < CITY_COUNT; i++) {
                cityStmt.setInt(1, i);
                cityStmt.setString(2, "City" + i);
                cityStmt.setDouble(3, rand.nextDouble() * 1000);
                cityStmt.setDouble(4, rand.nextDouble() * 1000);
                cityStmt.addBatch();
            }
            cityStmt.executeBatch();

            for (int i = 0; i < CITY_COUNT; i++) {
                for (int j = i + 1; j < CITY_COUNT; j++) {
                    if (rand.nextDouble() < SISTER_PROB) {
                        sisterStmt.setInt(1, i);
                        sisterStmt.setInt(2, j);
                        sisterStmt.addBatch();
                    }
                }
            }
            sisterStmt.executeBatch();
        }
    }
}
