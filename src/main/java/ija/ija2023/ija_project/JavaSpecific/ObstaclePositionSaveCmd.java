/**
 * Class containing simulation command used for logging changes in position
 * and size for Obstacles
 *
 * @author Denis Fekete (xfeket01@fit.vutbr.cz)
 */

package ija.ija2023.ija_project.JavaSpecific;

public class ObstaclePositionSaveCmd extends Command {
    /**
     * X position of Obstacle
     */
    double x;

    /**
     * Y position of Obstacle
     */
    double y;

    /**
     * Rotation of Obstacle
     */
    double rot;

    /**
     * Width of Obstacle
     */
    double w;

    /**
     * Height of Obstacle
     */
    double h;

    /**
     * Constructor of ObstaclePositionSaveCmd
     * @param obstacle Obstacle from which data will be stored
     * @param logId Current log id in simulation
     */
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