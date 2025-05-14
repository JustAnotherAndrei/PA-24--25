import database.*;
import graph.GraphAnalyzer;
import javafx.application.Application;
import ui.CityMapViewer;
import util.DataGenerator;

import java.sql.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        DatabaseManager db = new DatabaseManager();
        db.connect();
        db.createTables();

        DataGenerator.generate(db);

        // Load data from DB
        List<City> cities = new ArrayList<>();
        Map<Integer, List<Integer>> graph = new HashMap<>();
        Connection conn = db.getConnection();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM city")) {
            while (rs.next()) {
                City city = new City(rs.getInt("id"), rs.getString("name"), rs.getDouble("x"), rs.getDouble("y"));
                cities.add(city);
                graph.put(city.id, new ArrayList<>());
            }
        }

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM sister")) {
            while (rs.next()) {
                int c1 = rs.getInt("city1");
                int c2 = rs.getInt("city2");
                graph.get(c1).add(c2);
                graph.get(c2).add(c1);
            }
        }

        // Graph Analysis
        GraphAnalyzer analyzer = new GraphAnalyzer(cities.size());
        for (int id : graph.keySet()) {
            for (int neighbor : graph.get(id)) {
                if (id < neighbor)
                    analyzer.addEdge(id, neighbor);
            }
        }

        List<Set<Integer>> components = analyzer.find2ConnectedComponents();

        // UI Setup
        CityMapViewer.cities = cities;
        CityMapViewer.connections = graph;
        CityMapViewer.components = components;
        Application.launch(CityMapViewer.class);
    }
}
