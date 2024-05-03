package ija.ija2023.ija_project.JavaSpecific;

public class UnpauseCommandManual extends UnpauseCommandAuto
{
    double desiredAngle;

    boolean spinAnticlockwise;

    boolean spinClockwise;
    UnpauseCommandManual(BaseRobot robot, int logId)
    {
        super(robot, logId);
        this.type = CommandType.POSITION_CHANGE_MANUAL;
        this.logId = logId;

        this.desiredAngle = ((ManualRobot)robot).getDesiredAngle();
        this.spinClockwise = ((ManualRobot)robot).isSpinClockwise();
        this.spinAnticlockwise = ((ManualRobot)robot).isSpinAnticlockwise();
    }
}
