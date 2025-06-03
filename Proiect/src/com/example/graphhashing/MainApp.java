// File: src/com/example/graphhashing/MainApp.java
package com.example.graphhashing;

import com.example.graphhashing.dao.DatabaseManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Punctul de intrare al aplicației JavaFX.
 * Inițializează baza de date și apoi încarcă GUI-ul din FXML.
 */
public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // 1. Inițializează (sau creează) baza de date și tabelele
        DatabaseManager.getInstance().initializeDatabase();

        // 2. Încarcă layout-ul FXML
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/graphhashing/ui/graph_layout.fxml")
        );
        Scene scene = new Scene(loader.load());
        primaryStage.setTitle("GraphHashing: WL Hash & Isomorphism Tester");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        // La închidere, închide conexiunea la baza de date
        DatabaseManager.getInstance().closeConnection();
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
