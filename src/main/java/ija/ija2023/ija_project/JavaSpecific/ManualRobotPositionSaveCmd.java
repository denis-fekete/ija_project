/**
 * Class containing simulation command used for logging of ManualRobots
 *
 * @author Denis Fekete (xfeket01@fit.vutbr.cz)
 */

package ija.ija2023.ija_project.JavaSpecific;

public class ManualRobotPositionSaveCmd extends AutoRobotPositionSaveCmd
{
    /**
     * Desired angle of ManualRobot
     */
    double desiredAngle;

    /**
     * Value of spinAnticlockwise of ManualRobot
     */
    boolean spinAnticlockwise;

    /**
     * Value of spinClockwise of ManualRobot
     */
    boolean spinClockwise;

    /**
     * Constructor of ManualRobotPositionSaveCmd
     * @param robot Robot from which values will be copied
     * @param logId Current log id in simulation
     */
    ManualRobotPositionSaveCmd(BaseRobot robot, int logId)
    {
        super(robot, logId);
        this.type = CommandType.SAVE_MANUAL;
        this.logId = logId;

        this.desiredAngle = ((ManualRobot)robot).getDesiredAngle();
        this.spinClockwise = ((ManualRobot)robot).isSpinClockwise();
        this.spinAnticlockwise = ((ManualRobot)robot).isSpinAnticlockwise();
    }
}
