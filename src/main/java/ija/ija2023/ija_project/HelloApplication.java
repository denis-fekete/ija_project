package ija.ija2023.ija_project;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.shape.Circle;

import javafx.scene.paint.*;
import javafx.scene.layout.*;

import ija.ija2023.ija_project.JavaSpecific.Simulator;

public class HelloApplication extends Application {
    public int windowWidth = 600;
    public int windowHeight = 400;
    public Pane createContent()
    {
        Pane root = new Pane();
//        Canvas canvas = new Canvas(windowWidth, windowHeight);
//        GraphicsContext gc = canvas.getGraphicsContext2D();
        Simulator simulator = new Simulator(root, windowWidth, windowHeight);

        Button addRobotBtn = new Button("Add robot");
        addRobotBtn.setOnMouseClicked((event) -> {
            simulator.addAutomaticRobot(0,  100, 20, 0, 40,
                    Color.BLUE, 20, 1.1, 1);
        });
//
        Button addObstacleBtn = new Button("Add obstacle");
        addObstacleBtn.setTranslateY(40);
        addObstacleBtn.setOnMouseClicked((event) -> {
            simulator.addObstacle(200,  100, 40, 40, 0, Color.RED);
        });
//
        Button rotateRobot = new Button("Rotate robot");
        rotateRobot.setTranslateY(60);
//        rotateRobot.setOnMouseClicked((event) -> {
//            simulator.getRobots().get(0).rotateRobot();
//        });

//        Circle circle = new Circle(0,100, 40, Color.BLACK);
//        circle.setOnMouseDragged(e -> {
//            double offsetX = e.getSceneX() - circle.getCenterX();
//            double offsetY = e.getSceneY() - circle.getCenterY();
//            double newTranslateX = circle.getTranslateX() + offsetX;
//            double newTranslateY = circle.getTranslateY() + offsetY;
//
//            ((Circle)(e.getSource())).setTranslateX(newTranslateX);
//            ((Circle)(e.getSource())).setTranslateY(newTranslateY);
//        });
//        root.getChildren().add(circle);

        root.getChildren().addAll(addRobotBtn, addObstacleBtn, rotateRobot);

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