package com.financemanager;

import com.financemanager.controllers.SQLController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        // Set window dimensions based on screen dimensions
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenHeight = screenSize.getHeight() * 0.85;
        double screenWidth = screenSize.getWidth() * 0.35;

        scene = new Scene(loadFXML("views/Login"), screenWidth, screenHeight);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        var sqlController = new SQLController();

        try {
            sqlController.loadApplicationProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            sqlController.connectToDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            sqlController.closeDatabaseConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        launch();
    }

}