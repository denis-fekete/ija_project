package ija.ija2023.ija_project;

import java.io.IOException;
import java.util.BitSet;

import ija.ija2023.ija_project.visualization.AutomaticRobot;
import ija.ija2023.ija_project.visualization.Obstacle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.layout.*;

import javafx.animation.AnimationTimer;

import ija.ija2023.ija_project.visualization.Simulator;

public class HelloApplication extends Application {
    public int windowWidth = 600;
    public int windowHeight = 400;
    public VBox createContent()
    {
        VBox root = new VBox();
        Canvas canvas = new Canvas(windowWidth, windowHeight);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Simulator simulator = new Simulator(gc, windowWidth, windowHeight);

        Button addRobotBtn = new Button("Add robot");
        addRobotBtn.setOnMouseClicked((event) -> {
            simulator.addRobot(new AutomaticRobot(20,  100, 20, 90, 20,
                    Color.BLUE, 0.25, 45, true, simulator.getObstacles()) );
        });

        Button addObstacleBtn = new Button("Add obstacle");
        addObstacleBtn.setOnMouseClicked((event) -> {
            simulator.addObstacle(new Obstacle(200,  100, 40, 40, 45, Color.RED));
        });

        Button rotateRobot = new Button("Rotate robot");
        rotateRobot.setOnMouseClicked((event) -> {
            simulator.getRobots().get(0).rotateRobot();
        });

        root.getChildren().addAll(canvas, addRobotBtn, addObstacleBtn, rotateRobot);

        simulator.start();
        return root;
    }

    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        Scene scene = new Scene(createContent(), 640, 420);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}