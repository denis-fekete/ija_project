package ija.ija2023.ija_project.visualization;

import ija.ija2023.ija_project.simulation.MyRectangle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Diony
 */
public class Obstacle {
    MyRectangle sim;
    Color color;

    public Obstacle(double x, double y, double w, double h, double rot, Color color)
    {
        sim = MyRectangle.create(x, y, w, h, rot);
        this.color = color;
    }

    public void draw(GraphicsContext gc)
    {
        gc.setFill(color);
        gc.fillPolygon(sim.simulationColliderXPoints(), sim.simulationColliderYPoints(), 4);
    }

    public MyRectangle getSim() {
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
