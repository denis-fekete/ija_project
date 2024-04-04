package ija.ija2023.ija_project.visualization;

import ija.ija2023.ija_project.simulation.Point;
import ija.ija2023.ija_project.simulation.MyRectangle;
import ija.ija2023.ija_project.simulation.Robot;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;


import java.util.ArrayList;


/**
 *
 * @author Diony
 */
public class AutomaticRobot {
    Robot sim;
    double speed;
    Color color;
    double turnAngle;
    int turnDirection;

    ArrayList<Obstacle> obstacles;
    
    
    public AutomaticRobot( double x, double y, double radius, double rot,
            double detRadius, Color color, double speed,
            double turnAngle, boolean turnRight,
            ArrayList<Obstacle> obstaclesPointer)
    {
        this.sim = Robot.create(x, y, radius, rot, detRadius);
        this.speed = speed;
        this.color = color;
        this.turnAngle = turnAngle;
        this.turnDirection = (turnRight) ? 1 : -1;

        this.obstacles = obstaclesPointer;
    }

    public void rotateRobot()
    {
        sim.rotate(turnDirection * turnAngle);
    }

    public void moveRobot()
    {
        sim.moveForward(this.speed);
    }
    public void simulate(GraphicsContext gc)
    {
        boolean collision = sim.obstacleDetection(obstacles);
        if(collision)
        {
            rotateRobot();
        }
        else
        {
            moveRobot();
        }

        gc.setStroke(Color.BLACK);
        gc.strokePolygon(sim.colliderFwd.simulationColliderXPoints(), sim.colliderFwd.simulationColliderYPoints(), 4);
        gc.setFill(color);
        gc.fillArc(sim.getX(), sim.getY(), sim.getRadius(), sim.getRadius(), 0, 360, ArcType.ROUND);
    }

}
