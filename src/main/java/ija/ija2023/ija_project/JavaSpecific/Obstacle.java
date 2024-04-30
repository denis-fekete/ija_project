package ija.ija2023.ija_project.JavaSpecific;

import ija.ija2023.ija_project.SimulationLib2D.Rect;
import ija.ija2023.ija_project.SimulationLib2D.Point;
import javafx.scene.input.MouseEvent;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;

/**
 *
 * @author Diony
 */
public class Obstacle extends  javafx.scene.shape.Rectangle {

    /**
     * Rect object for simulation
     */
    Rect sim;

    /**
     * Color of this robot
     */
    Color color;

    /**
     * Color of this robot when highlighted
     */
    Color highlightedColor;

    /**
     * Reference to simulator
     */
    Simulator simulator;
    private Obstacle(double x, double y, double w, double h, double rot, Color color, Simulator simulator)
    {
        super(x, y, w, h);
        sim = Rect.create(x, y, w, h, rot);
        this.color = color;
        this.simulator = simulator;

        this.highlightedColor = Color.color(Math.min(color.getRed() + 0.15, 0.93),
                                    Math.min(color.getBlue() + 0.15, 0.93),
                                    Math.min(color.getGreen() + 0.15, 0.93));
    }


    public static Obstacle create(double x, double y, double w, double h, double rot, Color color, Simulator simulator)
    {
        Obstacle newObstacle = new Obstacle(x, y, w, h, rot, color, simulator);
        newObstacle.setFill(color);
        newObstacle.rotateObstacle(0);
        newObstacle.moveObstacleTo(newObstacle.sim.getPos());
        // robot main body is 3, robot collider is 1
        newObstacle.setViewOrder(2.0);

        newObstacle.setOnMouseClicked(mouseEvent -> {
            newObstacle.simulator.setActiveObstacle(newObstacle);
        });

        newObstacle.setStrokeType(StrokeType.INSIDE);

        newObstacle.setOnMouseDragged(mouseEvent -> {
            if(simulator.isPaused())
            {
                // Get the scene coordinates of the mouse event
                double sceneX = mouseEvent.getSceneX();
                double sceneY = mouseEvent.getSceneY();

                // Convert scene coordinates to coordinates relative to the parent pane
                javafx.geometry.Point2D cords = newObstacle.getParent().sceneToLocal(sceneX, sceneY);

                newObstacle.moveObstacleTo(new Point(cords.getX(), cords.getY()));
            }
        });

        return newObstacle;
    }

    public void rotateObstacle(double angle)
    {
        sim.rotate(angle);
        this.setRotate(sim.getRotation());
    }

    public void moveObstacleTo(Point p)
    {
        sim.moveTo(p);
        this.setX(sim.getX() - sim.getWidth() / 2);
        this.setY(sim.getY() - sim.getHeight() / 2);
    }

    public Rect getSim() {
        return sim;
    }

    @Override
    public String toString() {
        return "Obstacle(\n" +
                "sim=" + sim.toString() +
                "\ncolor=" + color.toString() +
                ')';
    }

    public void selectObstacle()
    {
        this.setStroke(highlightedColor);
        this.setStrokeWidth(5);
    }

    public void unselectObstacle()
    {
        this.setStroke(color);
        this.setStrokeWidth(1);
    }

}
