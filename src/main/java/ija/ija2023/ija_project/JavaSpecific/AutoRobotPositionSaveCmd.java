/**
 * Class containing position save in simulation, used for logging of AutoRobots
 *
 * @author Denis Fekete (xfeket01@fit.vutbr.cz)
 */

package ija.ija2023.ija_project.JavaSpecific;

public class AutoRobotPositionSaveCmd extends Command
{
    /**
     * X position of the robot
     */
    double x;

    /**
     * Y position of the robot
     */
    double y;

    /**
     * Speed of the robot
     */
    double speed;

    /**
     * Rotation of the robot
     */
    double rot;

    /**
     * TurnDirection of the robot
     */
    int turnDirection;

    /**
     * Radius of the robot
     */
    double radius;

    /**
     * Detection radius of the robot
     */
    double detectionRadius;

    /**
     * Turn speed of the robot
     */
    double turnSpeed;

    /**
     * Constructor of the AutoRobotPositionSaveCmd
     * @param robot Robot from which values will be copied
     * @param logId Current log ID in simulation
     */
    AutoRobotPositionSaveCmd(BaseRobot robot, int logId)
    {
        this.type = CommandType.SAVE_AUTO;
        this.logId = logId;

        this.x = robot.getSim().getX();
        this.y = robot.getSim().getY();
        this.rot = robot.getSim().getRotation();
        this.radius = robot.getSim().getRadius();
        this.detectionRadius = robot.getSim().getDetRadius();

        this.speed = robot.getSpeed();
        this.turnDirection = robot.getTurnDirection();
        this.turnSpeed = robot.getTurnSpeed();
    }
}
