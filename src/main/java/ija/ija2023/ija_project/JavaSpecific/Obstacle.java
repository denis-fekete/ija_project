/**
 * Implementation of Obstacle class. Obstacle is class for GUI representation
 * of Rectangle objects from simulation library, it is meant to be "interface"
 * between user and simulation that is calculated.
 *
 * @author Denis Fekete (xfeket01@fit.vutbr.cz)
 */

package ija.ija2023.ija_project.JavaSpecific;

import ija.ija2023.ija_project.SimulationLib2D.Rect;
import ija.ija2023.ija_project.SimulationLib2D.Point;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;

import java.util.ArrayList;

public class Obstacle extends  javafx.scene.shape.Rectangle {

    /**
     * Rect object for simulation
     */
    Rect sim;

    /**
     * Color of this robot
     */
    Color color;

    /**
     * Color of this robot when highlighted
     */
    Color highlightedColor;

    /**
     * Reference to simulator
     */
    Simulator simulator;

    /**
     * Array of Commands for logging and reverting simulation
     */
    ArrayList<Command> log;

    /**
     * Index of last log in for reverting simulation
     */
    int lastLogIndex;

    /**
     * Constructor of Obstacle object
     *
     * @param x              Center X position to be set
     * @param y              Center Y position to be set
     * @param w              Width of obstacle
     * @param h              Height of obstacle
     * @param rot            Rotation of this robot
     * @param color          Color of the AutoRobot
     * @param simulator      Simulator objects
     */
    private Obstacle(double x, double y, double w, double h, double rot, Color color, Simulator simulator)
    {
        super(x, y, w, h);
        sim = Rect.create(x, y, w, h, rot);
        this.color = color;
        this.simulator = simulator;

        this.highlightedColor = Color.color(Math.min(color.getRed() + 0.15, 0.93),
                                    Math.min(color.getBlue() + 0.15, 0.93),
                                    Math.min(color.getGreen() + 0.15, 0.93));
        log = new ArrayList<Command>();
        lastLogIndex = 0;
    }


    /**
     * Creates, instantiates and initializes Obstacle object, and returns it
     *
     * @param x              Center X position to be set
     * @param y              Center Y position to be set
     * @param w              Width of obstacle
     * @param h              Height of obstacle
     * @param rot            Rotation of this robot
     * @param color          Color of the AutoRobot
     * @param simulator      Simulator objects
     * @return               Created object
     */
    public static Obstacle create(double x, double y, double w, double h, double rot, Color color, Simulator simulator)
    {
        Obstacle newObstacle = new Obstacle(x, y, w, h, rot, color, simulator);
        newObstacle.setFill(color);
        newObstacle.rotateObstacle(rot);
        newObstacle.moveObstacleTo(newObstacle.sim.getPos());
        // robot main body is 3, robot collider is 1
        newObstacle.setViewOrder(2.0);

        newObstacle.setOnMousePressed(mouseEvent -> {
            newObstacle.simulator.setActiveObstacle(newObstacle);

            if(simulator.isPaused() && simulator.isSimulatingForward())
            {

                newObstacle.addLog(CommandType.SAVE_OBSTACLE, simulator.getLogId());
            }
        });

        newObstacle.setStrokeType(StrokeType.INSIDE);

        newObstacle.setOnMouseDragged(mouseEvent -> {
            if(simulator.isPaused() && simulator.isSimulatingForward())
            {
                // Get the scene coordinates of the mouse event
                double sceneX = mouseEvent.getSceneX();
                double sceneY = mouseEvent.getSceneY();

                // Convert scene coordinates to coordinates relative to the parent pane
                javafx.geometry.Point2D cords = newObstacle.getParent().sceneToLocal(sceneX, sceneY);

                newObstacle.moveObstacleTo(new Point(cords.getX(), cords.getY()));
            }
        });

        return newObstacle;
    }

    /**
     * Sets rotation of this Obstacle in simulation and its graphical
     * representation
     * @param angle New rotation to be set to the Obstacle
     */
    public void rotateObstacle(double angle)
    {
        sim.rotate(angle);
        this.setRotate(sim.getRotation());
    }

    /**
     * Moves obstacle to given point in simulation and graphical representation
     * @param p New center of the Obstacle
     */
    public void moveObstacleTo(Point p)
    {
        sim.moveTo(p);
        this.setX(sim.getX() - sim.getWidth() / 2);
        this.setY(sim.getY() - sim.getHeight() / 2);
    }

    /**
     * @return Returns simulation object of this Obstacle, Rect
     */
    public Rect getSim() {
        return sim;
    }

    /**
     * Selects this objects, making it highlighted
     */
    public void selectObstacle()
    {
        this.setStroke(highlightedColor);
        this.setStrokeWidth(5);
    }

    /**
     * Unselects this objects, making it un-highlighted
     */
    public void unselectObstacle()
    {
        this.setStroke(color);
        this.setStrokeWidth(1);
    }

    /**
     * Updates values of Obstacle
     * @param x New x position of Obstacle
     * @param y New y position of Obstacle
     * @param rot New rotation of Obstacle
     * @param w New width of rotation
     * @param h New height of rotation
     */
    public void updateObstacleValues(double x, double y,
                                   double rot, double w, double h)
    {
        sim.setRadius(rot);
        sim.setWidth(w);
        sim.setHeight(h);

        this.widthProperty().setValue(sim.getWidth());
        this.heightProperty().setValue(sim.getHeight());

        moveObstacleTo(new Point(x, y));
        rotateObstacle(rot);
    }

    /**
     * Adds new log into array of logs for reverse simulating
     * @param type Type of log to be added
     * @param logId Current log ID in simulation
     */
    public void addLog(CommandType type, int logId)
    {
        switch (type)
        {
            case START:
                log.add(new SimulationCommand(CommandType.START, logId));
                break;
            case PAUSE:
                log.add(new SimulationCommand(CommandType.PAUSE, logId));
                break;
            case SAVE_OBSTACLE:
                log.add(new ObstaclePositionSaveCmd(this, logId));
                break;
        }

        lastLogIndex = Math.max(log.size() - 1, 0);
    }

    /**
     * Sets parameters from Command into current Obstacle
     * @param cmd Command from which values will be taken from
     */
    public void setParameters(ObstaclePositionSaveCmd cmd)
    {
        updateObstacleValues(cmd.x, cmd.y, cmd.rot, cmd.w, cmd.h);
    }

    /**
     * Method that gets called when simulation is in reverse mode
     * @param logId Log id that should "reverse" simulate
     */
    public void reverseSimulate(int logId)
    {

        if(log.get(lastLogIndex).getType() == CommandType.START)
        {
            return;
        }

        while (log.get(lastLogIndex).getType() == CommandType.SAVE_OBSTACLE && log.get(lastLogIndex).logId == logId)
        {
            setParameters((ObstaclePositionSaveCmd) log.get(lastLogIndex));
            log.remove(lastLogIndex);
            lastLogIndex--;

            if(lastLogIndex < 0)
                return;
        }
    }
}
