package ija.ija2023.ija_project.JavaSpecific;

public class UnpauseCommandAuto extends Command
{
    double x;
    double y;
    double speed;
    double rot;
    int turnDirection;
    double radius;
    double detectionRadius;
    double turnSpeed;
    UnpauseCommandAuto(BaseRobot robot, int logId)
    {
        this.type = CommandType.POSITION_CHANGE_AUTO;
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
