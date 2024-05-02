/**
 * Implementation of AutoRobot class. AutoRobot is a specialization of
 * BaseRobot, which moves on its own and doesn't need user
 * interference (mostly doesn't allow it)
 *
 * @author Denis Fekete (xfeket01@fit.vutbr.cz)
 */

package ija.ija2023.ija_project.JavaSpecific;

import ija.ija2023.ija_project.SimulationLib2D.Rect;
import ija.ija2023.ija_project.SimulationLib2D.Robot;
import javafx.scene.paint.Color;


import java.util.ArrayList;


public class AutoRobot extends BaseRobot {

    /**
     * Constructor of AutoRobot object
     *
     * @param x              Center X position to be set
     * @param y              Center Y position to be set
     * @param radius         Radius if this Robot
     * @param rot            Rotation of this robot
     * @param detRadius      Detection radius of this robot
     * @param color          Color of the AutoRobot
     * @param speed          Speed of the AutoRobot
     * @param turnSpeed      Turn angle on collision detection
     * @param turnDirection  Turn direction, -1 or 1
     * @param obstacles      Vector of obstacles
     * @param robotColliders Vector of all robots
     * @param simulator      Simulator object
     */
    private AutoRobot(double x, double y, double radius, double rot,
                     double detRadius, Color color, double speed,
                     double turnSpeed, int turnDirection,
                     ArrayList<Rect> obstacles,
                     ArrayList<Robot> robotColliders,
                     Simulator simulator)
    {
        super(x, y, radius, rot, detRadius, color, speed, turnSpeed, turnDirection, obstacles, robotColliders, simulator);
    }

    /**
     * Creates an instance of AutoRobot, instantiates it and returns it
     *
     * @param x              Center X position to be set
     * @param y              Center Y position to be set
     * @param radius         Radius if this Robot
     * @param rot            Rotation of this robot
     * @param detRadius      Detection radius of this robot
     * @param color          Color of the AutoRobot
     * @param speed          Speed of the AutoRobot
     * @param turnSpeed      Turn angle on collision detection
     * @param turnDirection  Turn direction, -1 or 1
     * @param obstacles      Vector of obstacles
     * @param robotColliders Vector of all robots
     * @param simulator      Simulator object
     * @return Created object
     */
    public static AutoRobot create(double x, double y, double radius, double rot,
                     double detRadius, Color color, double speed,
                     double turnSpeed, int turnDirection,
                     ArrayList<Rect> obstacles,
                     ArrayList<Robot> robotColliders,
                     Simulator simulator)
    {
        AutoRobot newRobot = new AutoRobot(x, y, radius, rot, detRadius, color, speed, turnSpeed, turnDirection, obstacles, robotColliders, simulator);
        newRobot.initialize();

        return newRobot;
    }

    /**
     * One simulation cycle
     * @param deltaTime Time between frames
     */
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
