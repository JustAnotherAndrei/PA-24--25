// File: src/com/example/graphhashing/dao/HashResultDAO.java
package com.example.graphhashing.dao;

import java.sql.*;

/**
 * Clasă DAO pentru salvarea și preluarea rezultatelor de hashing WL.
 * Fiecare hash stochează (graph_id, hash_value, timestamp).
 */
public class HashResultDAO {
    private final Connection conn;

    public HashResultDAO() {
        this.conn = DatabaseManager.getInstance().getConnection();
    }

    /**
     * Salvează un hash WL pentru graful cu numele dat.
     * Mai întâi caută graph_id după nume, apoi inserează în HashResults.
     *
     * @param graphName numele grafului
     * @param hashValue valoarea hash WL
     * @throws SQLException pe eroare JDBC
     */
    public void saveHashResult(String graphName, String hashValue) throws SQLException {
        // 1) Obțin graph_id
        String selectGraph = "SELECT id FROM Graphs WHERE name = ?";
        long graphId;
        try (PreparedStatement selStmt = conn.prepareStatement(selectGraph)) {
            selStmt.setString(1, graphName);
            try (ResultSet rs = selStmt.executeQuery()) {
                if (!rs.next()) {
                    throw new SQLException("Graful '" + graphName + "' nu există în DB.");
                }
                graphId = rs.getLong("id");
            }
        }

        // 2) Inserez în HashResults
        String insertHash = "INSERT INTO HashResults (graph_id, hash_value) VALUES (?, ?)";
        try (PreparedStatement insStmt = conn.prepareStatement(insertHash)) {
            insStmt.setLong(1, graphId);
            insStmt.setString(2, hashValue);
            insStmt.executeUpdate();
        }

        conn.commit();
    }

    /**
     * Returnează cel mai recent hash pentru graful cu numele dat (sau null dacă
     * nu există încă niciun hash salvat).
     *
     * @param graphName numele grafului
     * @return șirul hash sau null dacă nu există
     * @throws SQLException pe eroare JDBC
     */
    public String getLatestHash(String graphName) throws SQLException {
        String selectSql =
                "SELECT hr.hash_value " +
                        "FROM HashResults hr " +
                        "JOIN Graphs g ON hr.graph_id = g.id " +
                        "WHERE g.name = ? " +
                        "ORDER BY hr.computed_at DESC " +
                        "LIMIT 1";
        try (PreparedStatement stmt = conn.prepareStatement(selectSql)) {
            stmt.setString(1, graphName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("hash_value");
                } else {
                    return null;
                }
            }
        }
    }
}
