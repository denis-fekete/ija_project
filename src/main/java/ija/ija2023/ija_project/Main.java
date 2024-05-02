/**
 * Implementation of Main application class. Main is main class of this
 * application, initializing all data-structures and objects needed for
 * application and simulation run
 *
 * @author Denis Fekete (xfeket01@fit.vutbr.cz)
 */

package ija.ija2023.ija_project;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
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

    /**
     * Method that for initializing all internal objects and showing window
     * @param stage Stage of the window
     * @throws IOException
     */
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
        simulator = Simulator.create(controller.getSimulationPane(), controller);
        // add simulator to controller for calling of Simulator interface methods
        controller.setSimulator(simulator);
        // add stage to controller for popup windows
        controller.setMainWindowStage(stage);


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

        // apply default world size restrictions
        controller.world_apply_world_border();

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    /**
     * @return Returns simulation object
     */
    public Simulator getSimulator()
    {
        return simulator;
    }
}