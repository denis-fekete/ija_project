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
    public void simulate(int logId)
    {
        if(sim.obstacleDetection(colliders) || sim.robotDetection(robotColliders) || isOutside())
        {
            rotateRobot(turnSpeed * turnDirection * SMOOTH_CONST);

            if(lastCommand != CommandType.ROTATE)
            {
                addLog(CommandType.ROTATE, logId);
            }
        }
        else
        {
            moveRobot(speed * SMOOTH_CONST);

            if(lastCommand != CommandType.MOVE)
            {
                addLog(CommandType.MOVE, logId);
            }
        }
    }

    public void reverseSimulate(int logId)
    {
        // check if logId parameter is same as lastLogIndex,
        // if yes set last to logId
        if(log.get(lastLogIndex - 1).logId == logId)
        {
            log.remove(lastLogIndex);
            lastLogIndex--;
        }

        if(log.get(lastLogIndex).getType() == CommandType.START)
        {
            return;
        }

        switch (log.get(lastLogIndex - 1).getType())
        {
            case MOVE:
                moveRobot(-speed * SMOOTH_CONST);
                break;
            case ROTATE:
                rotateRobot(turnSpeed * turnDirection * SMOOTH_CONST * (-1));
                break;
            case POSITION_CHANGE_AUTO:
                setParameters((UnpauseCommandAuto) log.get(lastLogIndex - 1));
                log.remove(lastLogIndex);
                lastLogIndex--;
                break;
            case POSITION_CHANGE_MANUAL:
                break;
        }
    }
}
