package ija.ija2023.ija_project;

import java.io.IOException;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javafx.scene.paint.*;
import javafx.scene.layout.*;

import ija.ija2023.ija_project.JavaSpecific.Simulator;

public class Main extends Application {
    /**
     * Simulator holding information about and object for simulation collisions
     */
    private Simulator simulator;

    /**
     * Pane of the scene, holding all objects, GUI, Robots, etc...
     */
    Pane root;

    @Override
    public void start(Stage stage) throws IOException {
        root = new Pane(); // pane storing all contents

        // load fxmlLoader
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("scene.fxml"));

        // add fxmlLoader into an root
        root.getChildren().add(fxmlLoader.load());

        // add controller set simulator reference into an controller
        Controller controller = fxmlLoader.getController();

        // create new simulation with scroll pane from FXML
        simulator = new Simulator(controller.getSimulationPane(), controller);
        // add simulator to controller for calling of Simulator interface methods
        controller.setSimulator(simulator);
        // add stage to controller for popup windows
        controller.setMainWindowStage(stage);

        // apply default world size restrictions
        controller.world_apply_world_border();

        // add scroll pane to the root
        root.getChildren().add(simulator.getScrollPane());
        // set scroll pane to be movable by user
        simulator.getScrollPane().setPannable(true);

        // create new scene and put contents of the root into it
        Scene scene = new Scene(root);

        stage.setTitle("IJA Project - 2D Collision Simulator");

        stage.widthProperty().addListener((observable, oldValue, newValue) -> {
            controller.resizeContents(newValue, null);
        });

        stage.heightProperty().addListener((observable, oldValue, newValue) -> {
            controller.resizeContents(null, newValue);
        });

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