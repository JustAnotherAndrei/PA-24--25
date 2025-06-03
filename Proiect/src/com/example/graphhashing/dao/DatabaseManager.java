// File: src/com/example/graphhashing/dao/DatabaseManager.java
package com.example.graphhashing.dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

/**
 * Singleton care gestionează conexiunea JDBC la fișierul SQLite și creează
 * tabelele necesare dacă nu există.
 */
public class DatabaseManager {
    private static final String DB_DIR = "data";
    private static final String DB_FILE = "data/graphdb.sqlite";
    private static final String JDBC_URL = "jdbc:sqlite:" + DB_FILE;

    private static DatabaseManager instance;
    private Connection connection;

    private DatabaseManager() {
        // constructor privat
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    /**
     * Creează folderul 'data/' și fișierul SQLite, dacă nu există,
     * apoi rulează DDL pentru tabele (dacă ele nu există deja).
     */
    public void initializeDatabase() {
        try {
            // 1) Creăm directorul data/ dacă lipsește
            Files.createDirectories(Paths.get(DB_DIR));

            // 2) Conectăm (sau creăm fișierul)
            connection = DriverManager.getConnection(JDBC_URL);
            connection.setAutoCommit(false);

            // 3) Activăm suportul pentru foreign keys
            try (Statement pragmaStmt = connection.createStatement()) {
                pragmaStmt.execute("PRAGMA foreign_keys = ON;");
            }

            // 4) Creăm tabelele, dacă nu există
            try (Statement stmt = connection.createStatement()) {
                // Tabelă Graphs
                stmt.executeUpdate(
                        "CREATE TABLE IF NOT EXISTS Graphs (" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "name TEXT NOT NULL UNIQUE" +
                                ");"
                );
                // Tabelă Nodes
                stmt.executeUpdate(
                        "CREATE TABLE IF NOT EXISTS Nodes (" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "graph_id INTEGER NOT NULL, " +
                                "label TEXT NOT NULL, " +
                                "FOREIGN KEY (graph_id) REFERENCES Graphs(id) ON DELETE CASCADE" +
                                ");"
                );
                // Tabelă Edges
                stmt.executeUpdate(
                        "CREATE TABLE IF NOT EXISTS Edges (" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "graph_id INTEGER NOT NULL, " +
                                "source_node_id INTEGER NOT NULL, " +
                                "target_node_id INTEGER NOT NULL, " +
                                "FOREIGN KEY (graph_id) REFERENCES Graphs(id) ON DELETE CASCADE, " +
                                "FOREIGN KEY (source_node_id) REFERENCES Nodes(id) ON DELETE CASCADE, " +
                                "FOREIGN KEY (target_node_id) REFERENCES Nodes(id) ON DELETE CASCADE" +
                                ");"
                );
                // Tabelă HashResults
                stmt.executeUpdate(
                        "CREATE TABLE IF NOT EXISTS HashResults (" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "graph_id INTEGER NOT NULL, " +
                                "hash_value TEXT NOT NULL, " +
                                "computed_at DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                                "FOREIGN KEY (graph_id) REFERENCES Graphs(id) ON DELETE CASCADE" +
                                ");"
                );

                connection.commit();
            }
        } catch (IOException | SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Eroare la inițializarea bazei de date: " + ex.getMessage());
        }
    }

    /**
     * Returnează conexiunea activă; dacă este null, apelează initializeDatabase().
     */
    public Connection getConnection() {
        if (connection == null) {
            initializeDatabase();
        }
        return connection;
    }

    /**
     * Închide conexiunea la baza de date.
     */
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
