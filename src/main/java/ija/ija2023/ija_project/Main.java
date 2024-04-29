package ija.ija2023.ija_project;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javafx.scene.paint.*;
import javafx.scene.layout.*;

import ija.ija2023.ija_project.JavaSpecific.Simulator;

public class Main extends Application {
    public int windowWidth = 600;
    public int windowHeight = 400;

    private Simulator simulator;

    Pane root;

    @Override
    public void start(Stage stage) throws IOException {
        // create needed objects
        Pane root = new Pane();
        simulator = new Simulator(root, windowWidth, windowHeight);

        // load fxmlLoader
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("scene.fxml"));

        // add fxmlLoader into an root
        root.getChildren().add(fxmlLoader.load());

        // add controller set simulator reference into an controller
        Controller controller = fxmlLoader.getController();
        controller.setSimulator(simulator);

        Scene scene = new Scene(root, 640, 420);

        stage.setTitle("IJA Project - 2D Collision Simulator");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public Simulator getSimulator()
    {
        return simulator;
    }
}