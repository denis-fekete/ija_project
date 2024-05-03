package ija.ija2023.ija_project.JavaSpecific;

public class ManualRobotPositionSaveCmd extends AutoRobotPositionSaveCmd
{
    double desiredAngle;

    boolean spinAnticlockwise;

    boolean spinClockwise;
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
