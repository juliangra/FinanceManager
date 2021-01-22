package com.financemanager;

import com.financemanager.controllers.SQLController;
import com.financemanager.models.Person;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 640, 480);
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

        var person = new Person(
                "John",
                "Doe",
                "john.doe@gmail.com",
                "securepassword",
                100d,
                123456789
        );

        try {
            sqlController.insert(person);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Person person1 = null;
        try {
            person1 = sqlController.read();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(person1);

        try {
            sqlController.closeDatabaseConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        //        todo.setDetails("congratulations, you have updated data!");
//        updateData(todo, connection);
//        deleteData(todo, connection);

        launch();
    }

}