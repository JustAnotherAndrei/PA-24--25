package ui;

import database.City;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.*;

public class CityMapViewer extends Application {
    public static List<City> cities;
    public static Map<Integer, List<Integer>> connections;
    public static List<Set<Integer>> components;

    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(1000, 1000);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        draw(gc);

        stage.setScene(new Scene(new Group(canvas)));
        stage.setTitle("City Map Viewer");
        stage.show();
    }

    private void draw(GraphicsContext gc) {
        Color[] palette = {Color.RED, Color.BLUE, Color.GREEN, Color.PURPLE, Color.ORANGE};

        Map<Integer, Integer> cityToComponent = new HashMap<>();
        for (int i = 0; i < components.size(); i++) {
            for (int cityId : components.get(i)) {
                cityToComponent.put(cityId, i);
            }
        }

        // Draw connections
        for (int i = 0; i < cities.size(); i++) {
            City c1 = cities.get(i);
            for (int neighbor : connections.getOrDefault(c1.id, new ArrayList<>())) {
                City c2 = cities.get(neighbor);
                int compIndex = cityToComponent.getOrDefault(c1.id, -1);
                gc.setStroke(compIndex >= 0 ? palette[compIndex % palette.length] : Color.GRAY);
                gc.strokeLine(c1.x, c1.y, c2.x, c2.y);
            }
        }

        // Draw cities
        for (City city : cities) {
            gc.setFill(Color.BLACK);
            gc.fillOval(city.x - 2, city.y - 2, 5, 5);
        }
    }
}

