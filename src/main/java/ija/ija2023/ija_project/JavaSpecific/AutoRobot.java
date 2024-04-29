package ija.ija2023.ija_project.JavaSpecific;

import ija.ija2023.ija_project.SimulationLib2D.Rect;
import ija.ija2023.ija_project.SimulationLib2D.Robot;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Rectangle;


import java.util.ArrayList;


/**
 *
 * @author Diony
 */
public class AutoRobot extends BaseRobot {

    private AutoRobot(double x, double y, double radius, double rot,
                     double detRadius, Color color, double speed,
                     double turnAngle, int turnDirection,
                     ArrayList<Rect> obstacles,
                     ArrayList<Robot> robotColliders,
                     Simulator simulator)
    {
        super(x, y, radius, rot, detRadius, color, speed, turnAngle, turnDirection, obstacles, robotColliders, simulator);
    }

    public static AutoRobot create(double x, double y, double radius, double rot,
                     double detRadius, Color color, double speed,
                     double turnAngle, int turnDirection,
                     ArrayList<Rect> obstacles,
                     ArrayList<Robot> robotColliders,
                     Simulator simulator)
    {
        AutoRobot newRobot = new AutoRobot(x, y, radius, rot, detRadius, color, speed, turnAngle, turnDirection, obstacles, robotColliders, simulator);
        newRobot.rotateRobot(0);

        newRobot.colliderRect = new Rectangle(newRobot.sim.colliderFwd.getX(), newRobot.sim.colliderFwd.getY(), newRobot.sim.colliderFwd.getWidth(), newRobot.sim.colliderFwd.getHeight());
        newRobot.colliderRect.setFill(null);
        newRobot.colliderRect.setStroke(color);
        newRobot.colliderRect.setStrokeWidth(1);

        newRobot.unselectRobot();

        return newRobot;
    }

    public void simulate(double deltaTime)
    {
        if(sim.obstacleDetection(colliders) || sim.robotDetection(robotColliders))
        {
            rotateRobot(turnAngle * turnDirection);
        }
        else
        {
            moveRobot(deltaTime * speed);
        }

        this.setCenterX(sim.getX() + sim.getRadius());
        this.setCenterY(sim.getY() + sim.getRadius());

        this.colliderRect.setX(sim.colliderFwd.getX());
        this.colliderRect.setY(sim.colliderFwd.getY());
        this.colliderRect.setRotate(sim.getRotation());
    }

}
