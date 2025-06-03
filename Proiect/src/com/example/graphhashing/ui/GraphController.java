// File: src/com/example/graphhashing/ui/GraphController.java
package com.example.graphhashing.ui;

import com.example.graphhashing.algorithm.WeisfeilerLeman;
import com.example.graphhashing.dao.GraphDAO;
import com.example.graphhashing.dao.HashResultDAO;
import com.example.graphhashing.model.Edge;
import com.example.graphhashing.model.Graph;
import com.example.graphhashing.util.Utils;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Controller pentru fereastra principală. Gestionează două grafuri în memorie (g1, g2),
 * salvează/încarcă din BD, încarcă din fișier, calculează hash-urile în background,
 * și compară grafurile.
 */
public class GraphController {

    // === Controale FXML pentru Graph 1 ===
    @FXML private TextField graph1NameField;
    @FXML private TextField graph1NodeU;
    @FXML private TextField graph1NodeV;
    @FXML private TextField graph1FilePath;
    @FXML private TextArea graph1EdgesArea;
    @FXML private TextField graph1HashField;
    @FXML private TextField graph1LoadNameField;

    // === Controale FXML pentru Graph 2 ===
    @FXML private TextField graph2NameField;
    @FXML private TextField graph2NodeU;
    @FXML private TextField graph2NodeV;
    @FXML private TextField graph2FilePath;
    @FXML private TextArea graph2EdgesArea;
    @FXML private TextField graph2HashField;
    @FXML private TextField graph2LoadNameField;

    // Contor pentru rezultat comparație
    @FXML private Label compareResultLabel;

    // Grafele în memorie
    private Graph g1 = new Graph("Graph1");
    private Graph g2 = new Graph("Graph2");

    // Instanțe DAO
    private final GraphDAO graphDAO = new GraphDAO();
    private final HashResultDAO hashResultDAO = new HashResultDAO();

    // ==================== Graph 1 Event Handlers ====================

    @FXML
    private void onAddEdgeGraph1() {
        String u = graph1NodeU.getText().trim();
        String v = graph1NodeV.getText().trim();
        if (u.isEmpty() || v.isEmpty()) {
            showAlert("Error", "Both node labels must be non-empty.", Alert.AlertType.ERROR);
            return;
        }
        g1.addEdge(u, v);
        updateEdgesDisplay(g1, graph1EdgesArea);
        graph1NodeU.clear();
        graph1NodeV.clear();
    }

    @FXML
    private void onSaveGraph1() {
        String name = graph1NameField.getText().trim();
        if (name.isEmpty()) {
            showAlert("Error", "Graph name cannot be empty.", Alert.AlertType.ERROR);
            return;
        }
        g1.setName(name);
        try {
            graphDAO.saveGraph(g1);
            showAlert("Success", "Graph '" + name + "' saved to database.", Alert.AlertType.INFORMATION);
        } catch (SQLException ex) {
            ex.printStackTrace();
            showAlert("Error", "Failed to save Graph1: " + ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void onLoadGraph1FromFile() {
        String path = graph1FilePath.getText().trim();
        String name = graph1NameField.getText().trim();
        if (path.isEmpty() || name.isEmpty()) {
            showAlert("Error", "Provide both file path and graph name.", Alert.AlertType.ERROR);
            return;
        }
        try {
            g1 = Utils.loadGraphFromEdgeList(path, name);
            updateEdgesDisplay(g1, graph1EdgesArea);
            graph1HashField.clear();
            showAlert("Success", "Graph loaded from file with name '" + name + "'.", Alert.AlertType.INFORMATION);
        } catch (IOException ex) {
            ex.printStackTrace();
            showAlert("Error", "Failed to load from file: " + ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void onLoadGraph1FromDB() {
        String name = graph1LoadNameField.getText().trim();
        if (name.isEmpty()) {
            showAlert("Error", "Enter a graph name to load.", Alert.AlertType.ERROR);
            return;
        }
        try {
            Graph loaded = graphDAO.loadGraph(name);
            if (loaded == null) {
                showAlert("Error", "Graph '" + name + "' not found in DB.", Alert.AlertType.ERROR);
                return;
            }
            g1 = loaded;
            graph1NameField.setText(name);
            updateEdgesDisplay(g1, graph1EdgesArea);

            // Ia hash-ul cel mai recent, dacă există
            String latestHash = hashResultDAO.getLatestHash(name);
            if (latestHash != null) {
                graph1HashField.setText(latestHash);
            } else {
                graph1HashField.clear();
            }

            showAlert("Success", "Graph '" + name + "' loaded from database.", Alert.AlertType.INFORMATION);
        } catch (SQLException ex) {
            ex.printStackTrace();
            showAlert("Error", "Failed to load from DB: " + ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void onComputeHashGraph1() {
        if (g1.nodeCount() == 0) {
            showAlert("Error", "Graph1 is empty. Add nodes/edges or load a graph first.", Alert.AlertType.ERROR);
            return;
        }
        // Rulează hash-ul WL în thread separat
        Task<String> task = new Task<>() {
            @Override
            protected String call() throws Exception {
                return WeisfeilerLeman.computeHash(g1);
            }
        };
        task.setOnSucceeded(e -> {
            String hash = task.getValue();
            graph1HashField.setText(hash);
            try {
                hashResultDAO.saveHashResult(g1.getName(), hash);
            } catch (SQLException ex) {
                ex.printStackTrace();
                showAlert("Error", "Failed to save hash result: " + ex.getMessage(), Alert.AlertType.ERROR);
            }
        });
        task.setOnFailed(e -> {
            Throwable ex = task.getException();
            ex.printStackTrace();
            showAlert("Error", "Hash computation failed: " + ex.getMessage(), Alert.AlertType.ERROR);
        });
        new Thread(task).start();
    }

    // ==================== Graph 2 Event Handlers ====================

    @FXML
    private void onAddEdgeGraph2() {
        String u = graph2NodeU.getText().trim();
        String v = graph2NodeV.getText().trim();
        if (u.isEmpty() || v.isEmpty()) {
            showAlert("Error", "Both node labels must be non-empty.", Alert.AlertType.ERROR);
            return;
        }
        g2.addEdge(u, v);
        updateEdgesDisplay(g2, graph2EdgesArea);
        graph2NodeU.clear();
        graph2NodeV.clear();
    }

    @FXML
    private void onSaveGraph2() {
        String name = graph2NameField.getText().trim();
        if (name.isEmpty()) {
            showAlert("Error", "Graph name cannot be empty.", Alert.AlertType.ERROR);
            return;
        }
        g2.setName(name);
        try {
            graphDAO.saveGraph(g2);
            showAlert("Success", "Graph '" + name + "' saved to database.", Alert.AlertType.INFORMATION);
        } catch (SQLException ex) {
            ex.printStackTrace();
            showAlert("Error", "Failed to save Graph2: " + ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void onLoadGraph2FromFile() {
        String path = graph2FilePath.getText().trim();
        String name = graph2NameField.getText().trim();
        if (path.isEmpty() || name.isEmpty()) {
            showAlert("Error", "Provide both file path and graph name.", Alert.AlertType.ERROR);
            return;
        }
        try {
            g2 = Utils.loadGraphFromEdgeList(path, name);
            updateEdgesDisplay(g2, graph2EdgesArea);
            graph2HashField.clear();
            showAlert("Success", "Graph loaded from file with name '" + name + "'.", Alert.AlertType.INFORMATION);
        } catch (IOException ex) {
            ex.printStackTrace();
            showAlert("Error", "Failed to load from file: " + ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void onLoadGraph2FromDB() {
        String name = graph2LoadNameField.getText().trim();
        if (name.isEmpty()) {
            showAlert("Error", "Enter a graph name to load.", Alert.AlertType.ERROR);
            return;
        }
        try {
            Graph loaded = graphDAO.loadGraph(name);
            if (loaded == null) {
                showAlert("Error", "Graph '" + name + "' not found in DB.", Alert.AlertType.ERROR);
                return;
            }
            g2 = loaded;
            graph2NameField.setText(name);
            updateEdgesDisplay(g2, graph2EdgesArea);

            String latestHash = hashResultDAO.getLatestHash(name);
            if (latestHash != null) {
                graph2HashField.setText(latestHash);
            } else {
                graph2HashField.clear();
            }

            showAlert("Success", "Graph '" + name + "' loaded from database.", Alert.AlertType.INFORMATION);
        } catch (SQLException ex) {
            ex.printStackTrace();
            showAlert("Error", "Failed to load from DB: " + ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void onComputeHashGraph2() {
        if (g2.nodeCount() == 0) {
            showAlert("Error", "Graph2 is empty. Add nodes/edges sau load a graph first.", Alert.AlertType.ERROR);
            return;
        }
        Task<String> task = new Task<>() {
            @Override
            protected String call() throws Exception {
                return WeisfeilerLeman.computeHash(g2);
            }
        };
        task.setOnSucceeded(e -> {
            String hash = task.getValue();
            graph2HashField.setText(hash);
            try {
                hashResultDAO.saveHashResult(g2.getName(), hash);
            } catch (SQLException ex) {
                ex.printStackTrace();
                showAlert("Error", "Failed to save hash result: " + ex.getMessage(), Alert.AlertType.ERROR);
            }
        });
        task.setOnFailed(e -> {
            Throwable ex = task.getException();
            ex.printStackTrace();
            showAlert("Error", "Hash computation failed: " + ex.getMessage(), Alert.AlertType.ERROR);
        });
        new Thread(task).start();
    }

    // ==================== Comparație Grafuri ====================

    @FXML
    private void onCompareGraphs() {
        String h1 = graph1HashField.getText().trim();
        String h2 = graph2HashField.getText().trim();
        if (h1.isEmpty() || h2.isEmpty()) {
            showAlert("Error", "Compute both hashes before comparing.", Alert.AlertType.ERROR);
            return;
        }
        boolean iso = WeisfeilerLeman.areIsomorphic(g1, g2);
        if (iso) {
            compareResultLabel.setText("Isomorphic ✔");
            compareResultLabel.setStyle("-fx-text-fill: green; -fx-font-size: 16px; -fx-font-weight: bold;");
        } else {
            compareResultLabel.setText("Not Isomorphic ✘");
            compareResultLabel.setStyle("-fx-text-fill: red; -fx-font-size: 16px; -fx-font-weight: bold;");
        }
    }

    // ==================== Metode Helper ====================

    /**
     * Actualizează TextArea-ul pentru a afișa toate muchiile grafului, câte una pe linie.
     */
    private void updateEdgesDisplay(Graph graph, TextArea area) {
        StringBuilder sb = new StringBuilder();
        for (Edge edge : graph.getEdges()) {
            sb.append(edge.getU().getLabel())
                    .append(" -- ")
                    .append(edge.getV().getLabel())
                    .append("\n");
        }
        area.setText(sb.toString());
    }

    /**
     * Afișează un dialog de tip Alert cu titlu, mesaj și tip specificat.
     */
    private void showAlert(String title, String content, Alert.AlertType type) {
        Platform.runLater(() -> {
            Alert alert = new Alert(type);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(content);
            alert.showAndWait();
        });
    }
}
