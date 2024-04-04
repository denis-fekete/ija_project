package ija.ija2023.ija_project.visualization;

import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.Pane;

/**
 *
 * @author Diony
 */
public class Simulator extends AnimationTimer {
    ArrayList<Obstacle> obstacles;
    ArrayList<AutomaticRobot> robots;
    GraphicsContext gc;
    int spaceWidth;
    int spaceHeight;
    long lastUpdate;
    public Simulator(GraphicsContext gc, int width, int height)
    {
        obstacles = new ArrayList<Obstacle>();
        robots = new ArrayList<AutomaticRobot>();
        lastUpdate = 0;

        this.gc = gc;
        this.spaceWidth = width;
        this.spaceHeight = height;
    }

    public Simulator addObstacle(Obstacle obstacle)
    {
        obstacles.add(obstacle);
        return this;
    }

    public Simulator addRobot(AutomaticRobot robot)
    {
        robots.add(robot);
        return this;
    }

    @Override
    public void handle(long now) 
    {
        double deltaTime = (now - lastUpdate) / 1e9;

        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0, 0, spaceWidth, spaceHeight);

        for(AutomaticRobot robot : robots)
        {
            robot.simulate(gc);

        }

        for(Obstacle obstacle: obstacles)
        {
            obstacle.draw(gc);
        }

        lastUpdate = now;
    }

    public ArrayList<Obstacle> getObstacles() {
        return obstacles;
    }

    public ArrayList<AutomaticRobot> getRobots() {
        return robots;
    }
}
