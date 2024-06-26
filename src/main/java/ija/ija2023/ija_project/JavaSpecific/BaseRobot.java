/**
 * Implementation of BaseRobot class. BaseRobot is class for graphical representation of Robots from simulation
 * library, it is meant as a interface between user and simulations that are
 * calculated. BaseRobot is not meant to be instantiated or user, it is meant
 * as a base class for specialization of Robots.
 *
 * @author Denis Fekete (xfeket01@fit.vutbr.cz)
 */

package ija.ija2023.ija_project.JavaSpecific;

import ija.ija2023.ija_project.SimulationLib2D.Point;
import ija.ija2023.ija_project.SimulationLib2D.Rect;
import ija.ija2023.ija_project.SimulationLib2D.Robot;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

import java.util.ArrayList;

public class BaseRobot extends javafx.scene.shape.Circle {
    /**
     * Robot object for simulation
     */
    Robot sim;

    /**
     * Graphical representation of collider
     */
    Rectangle colliderRect;

    /**
     * Color of this robot
     */
    Color color;

    /**
     * Color of this robot when highlighted
     */
    Color highlightedColor;

    /**
     * Reference to the simulator
     */
    Simulator simulator;

    /**
     * Speed of this robot
     */
    double speed;

    /**
     * Turn angle on collision detection
     */

    double turnSpeed;

    /**
     * Direction in which will robot turn on collision
     */
    int turnDirection;

    static double SMOOTH_CONST = 0.01;

    /**
     * Array of obstacles that robot can collide with
     */
    ArrayList<Rect> colliders;

    /**
     * Array of other robots that robot can collide with
     */
    ArrayList<Robot> robotColliders;

    /**
     * Last command that was saved into log
     */
    CommandType lastCommand;

    /**
     * Array of Commands for logging and reverting simulation
     */
    ArrayList<Command> log;

    /**
     * Index of last log in for reverting simulation
     */
    int lastLogIndex;

    /**
     * Constructor of BaseRobot object
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
    public BaseRobot(double x, double y, double radius, double rot,
                     double detRadius, Color color, double speed,
                     double turnSpeed, int turnDirection,
                     ArrayList<Rect> obstacles,
                     ArrayList<Robot> robotColliders,
                     Simulator simulator) {
        super(x, y, radius, color);

        this.sim = Robot.create(x, y, radius, rot, detRadius);
        this.speed = speed;
        this.color = color;
        this.turnSpeed = turnSpeed;
        this.turnDirection = turnDirection;
        this.colliders = obstacles;
        this.robotColliders = robotColliders;
        this.simulator = simulator;
        this.highlightedColor = Color.color(Math.min(color.getRed() + 0.15, 0.93),
                Math.min(color.getBlue() + 0.15, 0.93),
                Math.min(color.getGreen() + 0.15, 0.93));

        this.log = new ArrayList<Command>();
        lastCommand = CommandType.NONE;
        lastLogIndex = -1;
    }

    /**
     * Initializes this BaseRobot object, correctly rotates it,
     * sets drawing style, implements on drag function
     */
    protected void initialize()
    {
        // initialize collider
        this.colliderRect = new Rectangle(   sim.colliderFwd.getX(),
                                        sim.colliderFwd.getY(),
                                        sim.colliderFwd.getWidth(),
                                        sim.colliderFwd.getHeight());
        colliderRect.setFill(null);
        colliderRect.setStroke(color);
        colliderRect.setStrokeWidth(1);
        // set viewOrder to 1, to be above default values but below obstacles
        // nd main bodies of robots
        colliderRect.setViewOrder(3);

        // set viewOrder to 3, to be higher that collider and obstacles (2)
        this.setViewOrder(1);
        // initialize and correct position and rotation
        this.rotateRobot(0);
        this.moveRobotTo(sim.getPos());

        // set stroke to be from inside
        this.setStrokeType(StrokeType.INSIDE);
        this.unselectRobot();

        this.setOnMouseClicked(mouseEvent -> {
            this.simulator.setActiveRobot(this);
        });

        this.setOnMouseDragged(mouseEvent -> {
            if(simulator.isPaused() && simulator.isSimulatingForward())
            {
                // Get the scene coordinates of the mouse event
                double sceneX = mouseEvent.getSceneX();
                double sceneY = mouseEvent.getSceneY();

                // Convert scene coordinates to coordinates relative to the parent pane
                javafx.geometry.Point2D cords = this.getParent().sceneToLocal(sceneX, sceneY);

                this.moveRobotTo(new Point(cords.getX(), cords.getY()));
            }
        });

    }

    /**
     * Rotates simulation and graphical representation of the robot
     * @param angle Angle by which will be the robot rotated
     */
    public void rotateRobot(double angle)
    {
        sim.rotate(angle);
        this.setRotate(sim.getRotation());

        this.colliderRect.setX(sim.colliderFwd.getX() - sim.colliderFwd.getWidth() / 2);
        this.colliderRect.setY(sim.colliderFwd.getY() - sim.colliderFwd.getHeight() / 2);
        this.colliderRect.setRotate(sim.colliderFwd.getRotation());
    }

    /**
     * Moves simulation and graphical representation of the robot in forward
     * direction by distance
     * @param distance Distance that robot will be moved by
     */
    public void moveRobot(double distance)
    {
        sim.moveForward(distance);

        this.setCenterX(sim.getX());
        this.setCenterY(sim.getY());

        this.colliderRect.setX(sim.colliderFwd.getX() - sim.colliderFwd.getWidth() / 2);
        this.colliderRect.setY(sim.colliderFwd.getY() - sim.colliderFwd.getHeight() / 2);
    }

    /**
     * Moves simulation and graphical representation of the robot to given point
     * @param p Point which will be the new center of robot
     */
    public void moveRobotTo(Point p)
    {
        sim.moveTo(p);
        this.setCenterX(sim.getX());
        this.setCenterY(sim.getY());

        this.colliderRect.setX(sim.colliderFwd.getX() - sim.colliderFwd.getWidth() / 2);
        this.colliderRect.setY(sim.colliderFwd.getY() - sim.colliderFwd.getHeight() / 2);
        this.colliderRect.setRotate(sim.getRotation());
    }

    /**
     * Returns objects on simulation layer (Robot object), that calculates
     * simulation and collisions
     * @return Simulation of this robot
     */
    public Robot getSim() {
        return sim;
    }

    /**
     * Selects this robot and highlighting it
     */
    public void selectRobot()
    {
        this.setStroke(highlightedColor);
        this.setStrokeWidth(5);
    }

    /**
     * Unselects this robot and un-highlighting
     */
    public void unselectRobot()
    {
        this.setStroke(color);
        this.setStrokeWidth(1);
    }

    /**
     * @return Returns speed of this robot
     */
    public double getSpeed()
    {
        return this.speed;
    }

    /**
     * Sets speed of this robot
     * @param speed Value to be set
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * @return Returns turning speed of this robot
     */
    public double getTurnSpeed() {
        return turnSpeed;
    }

    /**
     * Sets new turning speed of the robot
     * @param turnSpeed New value to be set
     */
    public void setTurnSpeed(double turnSpeed) {
        this.turnSpeed = turnSpeed;
    }

    /**
     * Checks if robot is outside of the simulation
     * @return True if yes, false if no
     */
    public boolean isOutside()
    {
        return (sim.colliderFwd.getRB().getX() > simulator.getSpaceWidth() ||
                sim.colliderFwd.getRB().getY() > simulator.getSpaceHeight() ||
                sim.colliderFwd.getRT().getX() > simulator.getSpaceWidth() ||
                sim.colliderFwd.getRT().getY() > simulator.getSpaceHeight() ||

                sim.colliderFwd.getRB().getX() < 0 ||
                sim.colliderFwd.getRB().getY() < 0 ||
                sim.colliderFwd.getRT().getX() < 0 ||
                sim.colliderFwd.getRT().getY() < 0);
    }

    /**
     * @return Returns turn direction of the robot
     */
    public int getTurnDirection() {
        return turnDirection;
    }

    /**
     * Sets turn direction of robot
     * @param turnDirection New values
     */
    public void setTurnDirection(int turnDirection) {
        this.turnDirection = turnDirection;
    }

    /**
     * Updates robot values based on provided values
     * @param x New x position of robot
     * @param y New y position of robot
     * @param rad New radius of robot
     * @param rot New rotation of robot
     * @param detRad New detection radius of robot
     * @param speed New speed of robot
     * @param turnSpeed New turn speed of robot
     * @param turnDirection New turn direction of robot
     */
    public void updateRobotValues(double x, double y,
                                  double rad, double rot,
                                  double detRad, double speed,
                                  double turnSpeed, int turnDirection)
    {
        sim.setRadius(rad);
        sim.setDetRadius(detRad);
        this.setSpeed(speed);
        this.setTurnSpeed(turnSpeed);
        this.setTurnDirection(turnDirection);
        sim.setRotation(rot);

        moveRobotTo(new Point(x, y));

        sim.colliderFwd.setHeight(2 * sim.getRadius());
        sim.colliderFwd.setWidth(sim.getDetRadius());
        sim.colliderFwd.setX(sim.getX() + sim.getDetRadius() / 2);
        sim.colliderFwd.setY(sim.getY());

        this.radiusProperty().setValue(sim.getRadius());

        colliderRect.widthProperty().set(sim.colliderFwd.getWidth());
        colliderRect.heightProperty().set(sim.colliderFwd.getHeight());

        moveRobotTo(sim.getPos());
    }

    /**
     * Sets parameters from Command into current Robot
     * @param cmd Command from which values will be taken from
     */
    public void setParameters(AutoRobotPositionSaveCmd cmd)
    {
        updateRobotValues(cmd.x, cmd.y,
                cmd.radius, cmd.rot,
                cmd.detectionRadius, cmd.speed,
                cmd.turnSpeed, cmd.turnDirection);
    }

    /**
     * Simulates one simulation cycle of robot
     */
    public void simulate(int logId) {}

    /**
     * Adds new log into array of logs for reverse simulating
     * @param type Type of log to be added
     * @param logId Current log ID in simulation
     */
    public void addLog(CommandType type, int logId)
    {
        switch (type)
        {
            case MOVE:
                log.add(new SimulationCommand(CommandType.MOVE, logId));
                lastCommand = CommandType.MOVE;
                break;
            case ROTATE:
                log.add(new SimulationCommand(CommandType.ROTATE, logId));
                lastCommand = CommandType.ROTATE;
                break;
            case ROTATE_ANTI:
                log.add(new SimulationCommand(CommandType.ROTATE_ANTI, logId));
                lastCommand = CommandType.ROTATE_ANTI;
                break;
            case SAVE_AUTO:
                log.add(new AutoRobotPositionSaveCmd(this, logId));
                lastCommand = CommandType.SAVE_AUTO;
                break;
            case SAVE_MANUAL:
                log.add(new ManualRobotPositionSaveCmd(this, logId));
                lastCommand = CommandType.SAVE_MANUAL;
                break;
            case PAUSE:
                log.add(new SimulationCommand(CommandType.PAUSE, logId));
                break;
            case START:
                log.add(new SimulationCommand(CommandType.START, logId));
                break;
        }

        lastLogIndex = Math.max(log.size() - 1, 0);
    }

    /**
     * Method that gets called instead of simulate() when simulation is in
     * reverse mode
     * @param logId Log id that should "reverse" simulate
     */
    public void reverseSimulate(int logId)
    {

        if(lastLogIndex < 1)
            return;

        while(log.get(lastLogIndex - 1).logId == logId)
        {

            if(log.get(lastLogIndex - 1).getType() == CommandType.SAVE_AUTO)
            {
                setParameters((AutoRobotPositionSaveCmd) log.get(lastLogIndex - 1));
            }

            log.remove(lastLogIndex);
            lastLogIndex--;

            if(lastLogIndex < 1)
                return;
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
            case ROTATE_ANTI:
                rotateRobot(turnSpeed * turnDirection * SMOOTH_CONST);
                break;
        }
    }
}
