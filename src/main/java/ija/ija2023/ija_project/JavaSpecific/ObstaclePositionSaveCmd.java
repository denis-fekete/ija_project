package ija.ija2023.ija_project.JavaSpecific;

public class ObstaclePositionSaveCmd extends Command {
    double x;
    double y;
    double rot;

    double w;

    double h;

    ObstaclePositionSaveCmd(Obstacle obstacle, int logId)
    {
        this.type = CommandType.SAVE_OBSTACLE;
        this.logId = logId;

        this.x = obstacle.getSim().getX();
        this.y = obstacle.getSim().getY();
        this.rot = obstacle.getSim().getRotation();

        this.w = obstacle.getSim().getWidth();
        this.h = obstacle.getSim().getHeight();
    }
}