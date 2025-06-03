// File: src/com/example/graphhashing/dao/GraphDAO.java
package com.example.graphhashing.dao;

import com.example.graphhashing.model.Edge;
import com.example.graphhashing.model.Graph;
import com.example.graphhashing.model.Node;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Clasă DAO pentru salvarea și încărcarea obiectelor Graph în baza de date SQLite.
 * Graful este identificat printr-un nume unic.
 */
public class GraphDAO {
    private final Connection conn;

    public GraphDAO() {
        this.conn = DatabaseManager.getInstance().getConnection();
    }

    /**
     * Salvează un Graph (implicit cu noduri și muchii) în baza de date.
     * Dacă există deja un graf cu același nume, îl șterge mai întâi (cascade delete).
     *
     * @param graph obiectul Graph de salvat
     * @throws SQLException pe eroare JDBC
     */
    public void saveGraph(Graph graph) throws SQLException {
        // 1) Șterg un graf cu același nume (dacă există)
        String deleteSql = "DELETE FROM Graphs WHERE name = ?";
        try (PreparedStatement delStmt = conn.prepareStatement(deleteSql)) {
            delStmt.setString(1, graph.getName());
            delStmt.executeUpdate();
        }

        // 2) Inserez în tabelul Graphs
        String insertGraph = "INSERT INTO Graphs (name) VALUES (?)";
        long graphId;
        try (PreparedStatement insStmt = conn.prepareStatement(insertGraph, Statement.RETURN_GENERATED_KEYS)) {
            insStmt.setString(1, graph.getName());
            insStmt.executeUpdate();
            try (ResultSet rs = insStmt.getGeneratedKeys()) {
                rs.next();
                graphId = rs.getLong(1);
            }
        }

        // 3) Inserez noduri și țin evidența id-urilor generate
        String insertNode = "INSERT INTO Nodes (graph_id, label) VALUES (?, ?)";
        Map<String, Long> nodeIdMap = new HashMap<>(); // label -> node_id
        try (PreparedStatement insNodeStmt = conn.prepareStatement(insertNode, Statement.RETURN_GENERATED_KEYS)) {
            for (Node node : graph.getNodes()) {
                insNodeStmt.setLong(1, graphId);
                insNodeStmt.setString(2, node.getLabel());
                insNodeStmt.executeUpdate();
                try (ResultSet rs = insNodeStmt.getGeneratedKeys()) {
                    rs.next();
                    long dbNodeId = rs.getLong(1);
                    nodeIdMap.put(node.getLabel(), dbNodeId);
                }
            }
        }

        // 4) Inserez muchiile
        String insertEdge = "INSERT INTO Edges (graph_id, source_node_id, target_node_id) VALUES (?, ?, ?)";
        try (PreparedStatement insEdgeStmt = conn.prepareStatement(insertEdge)) {
            for (Edge edge : graph.getEdges()) {
                long srcId = nodeIdMap.get(edge.getU().getLabel());
                long tgtId = nodeIdMap.get(edge.getV().getLabel());
                insEdgeStmt.setLong(1, graphId);
                insEdgeStmt.setLong(2, srcId);
                insEdgeStmt.setLong(3, tgtId);
                insEdgeStmt.executeUpdate();
            }
        }

        conn.commit();
    }

    /**
     * Încarcă un Graph din baza de date, după numele său. Dacă nu există, returnează null.
     *
     * @param name numele grafului
     * @return obiect Graph încărcat sau null dacă nu există
     * @throws SQLException pe eroare JDBC
     */
    public Graph loadGraph(String name) throws SQLException {
        // 1) Caut graph_id pentru numele dat
        String selectGraph = "SELECT id FROM Graphs WHERE name = ?";
        long graphId = -1;
        try (PreparedStatement selStmt = conn.prepareStatement(selectGraph)) {
            selStmt.setString(1, name);
            try (ResultSet rs = selStmt.executeQuery()) {
                if (rs.next()) {
                    graphId = rs.getLong("id");
                } else {
                    return null; // graf inexistent
                }
            }
        }

        Graph graph = new Graph(name);

        // 2) Încarc nodurile (id -> label)
        String selectNodes = "SELECT id, label FROM Nodes WHERE graph_id = ?";
        Map<Long, String> idToLabel = new HashMap<>();
        try (PreparedStatement selNodesStmt = conn.prepareStatement(selectNodes)) {
            selNodesStmt.setLong(1, graphId);
            try (ResultSet rs = selNodesStmt.executeQuery()) {
                while (rs.next()) {
                    long nodeId = rs.getLong("id");
                    String label = rs.getString("label");
                    idToLabel.put(nodeId, label);
                    graph.addNode(label);
                }
            }
        }

        // 3) Încarc muchiile
        String selectEdges = "SELECT source_node_id, target_node_id FROM Edges WHERE graph_id = ?";
        try (PreparedStatement selEdgesStmt = conn.prepareStatement(selectEdges)) {
            selEdgesStmt.setLong(1, graphId);
            try (ResultSet rs = selEdgesStmt.executeQuery()) {
                while (rs.next()) {
                    long srcId = rs.getLong("source_node_id");
                    long tgtId = rs.getLong("target_node_id");
                    String srcLabel = idToLabel.get(srcId);
                    String tgtLabel = idToLabel.get(tgtId);
                    graph.addEdge(srcLabel, tgtLabel);
                }
            }
        }

        return graph;
    }
}
