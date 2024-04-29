package ija.ija2023.ija_project.JavaSpecific;

import ija.ija2023.ija_project.SimulationLib2D.Rect;
import ija.ija2023.ija_project.SimulationLib2D.Point;
import javafx.scene.paint.Color;

/**
 *
 * @author Diony
 */
public class Obstacle extends  javafx.scene.shape.Rectangle {
    Rect sim;
    Color color;

    private Obstacle(double x, double y, double w, double h, double rot, Color color)
    {
        super(x, y, w, h);
        sim = Rect.create(x, y, w, h, rot);
        this.color = color;
    }

    public static Obstacle create(double x, double y, double w, double h, double rot, Color color)
    {
        Obstacle newObstacle = new Obstacle(x, y, w, h, rot, color);
        newObstacle.setFill(color);
        newObstacle.rotateObstacle(0);

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
        this.setTranslateX(sim.getX());
        this.setTranslateY(sim.getY());
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
}
