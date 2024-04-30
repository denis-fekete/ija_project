package ija.ija2023.ija_project.JavaSpecific;

import ija.ija2023.ija_project.SimulationLib2D.Rect;
import ija.ija2023.ija_project.SimulationLib2D.Robot;
import javafx.scene.paint.Color;


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
        newRobot.initialize();

        return newRobot;
    }

    public void simulate(double deltaTime)
    {
        if(sim.obstacleDetection(colliders) || sim.robotDetection(robotColliders) || isOutside())
        {
            rotateRobot(turnSpeed * turnDirection * deltaTime);
        }
        else
        {
            moveRobot(deltaTime * speed);
        }
    }

}
