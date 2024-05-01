package ija.ija2023.ija_project.JavaSpecific;

import java.util.ArrayList;

import ija.ija2023.ija_project.Controller;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import ija.ija2023.ija_project.SimulationLib2D.*;

/**
 * Simulator is class responsible for managing Robots and Obstacles. It is
 * meant to have only one instance in program. It is main interface between
 * GUI and simulations that are calculated by SimulationLib2D classes.
 *
 * @author Denis Fekete (xfeket01@fit.vutbr.cz)
 */
public class Simulator extends AnimationTimer {
    /**
     * Array of all obstacles
     */
    ArrayList<Obstacle> obstacles;

    /**
     * Array of Rect colliders that robots can collide with
     */
    ArrayList<Rect> colliders;

    /**
     * Array of all Robot objects for other Robots to collide with
     */
    ArrayList<Robot> robotColliders;

    /**
     * Array of all robots, automated and manual
     */
    ArrayList<BaseRobot> robots;

    /**
     * Width of simulation space
     */
    double spaceWidth;

    /**
     * Height of simulation space
     */
    double spaceHeight;

    /**
     * Value of when was simulator last called
     */
    long lastUpdate;

    /**
     * Active/selected robot
     */
    BaseRobot activeRobot;

    /**
     * Active/selected obstacle
     */
    Obstacle activeObstacle;

    /**
     * Pane where robots and obstacles are stored
     */
    Pane world;

    /**
     * Scroll pane that will be displayed
     */
    ScrollPane scrollPane;

    /**
     *
     */
    Controller guiController;

    /**
     * Boolean value telling if the Simulator is pause or not
     */
    boolean running;

    /**
     * Constructor of simulation object that will simulation robot movement
     * and collisions between robots and obstacles
     * @param scrollPane Scroll pane to which the objects will be stored
     * @param controller Controller object to interact with GUI
     */
    public Simulator(ScrollPane scrollPane, Controller controller)
    {
        obstacles = new ArrayList<Obstacle>();
        colliders = new ArrayList<Rect>();

        robots = new ArrayList<BaseRobot>();

        robotColliders = new ArrayList<Robot>();

        activeRobot = null;
        activeObstacle = null;

        this.world = new Pane();

        this.scrollPane = scrollPane;
        this.guiController = controller;

        lastUpdate = 0;

        running = false;
    }

    /**
     * This methods gets called frequently by inherited class AnimationTimer
     * @param now Current time for calculating deltaTime
     */
    @Override
    public void handle(long now) 
    {
        double deltaTime = (now - lastUpdate) / 1e9;


        // normalized delta value, 0.3 to prevent big jump on resume
        double val = Math.min(deltaTime, 0.3);

        for(BaseRobot robot : robots)
        {
            robot.simulate(val);
        }

        lastUpdate = now;
    }


    /**
     * Adds new automatic robot the simulation
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
     */
    public void addAutomaticRobot(double x, double y, double radius, double rot,
                                     double detRadius, Color color, double speed,
                                     double turnSpeed, int turnDirection)
    {
        // create new robot
        AutoRobot newRobot = AutoRobot.create(x, y, radius, rot,
                detRadius, color, speed, turnSpeed, turnDirection,
                colliders, robotColliders, this);

        // add it to array of BaseRobots
        robots.add(newRobot);
        // add it to array of robot colliders
        robotColliders.add(newRobot.getSim());

        // add new robot to the scene
        world.getChildren().addAll(newRobot, newRobot.colliderRect);
        scrollPane.setContent(world);
    }

    /**
     * Adds new manual robot the simulation
     *
     * @param x              Center X position to be set
     * @param y              Center Y position to be set
     * @param radius         Radius if this Robot
     * @param rot            Rotation of this robot
     * @param detRadius      Detection radius of this robot
     * @param color          Color of the AutoRobot
     */
    public void addManualRobot(double x, double y, double radius, double rot,
                               double detRadius, Color color)
    {
        // create new robot, 10, 45, 1 = random default values
        ManualRobot newRobot = ManualRobot.create(x, y, radius, rot,
                detRadius, color, 0, 0, 1,
                false, false, rot,
                colliders, robotColliders, this);

        // add it to array of BaseRobots
        robots.add(newRobot);
        // add it to array of robot colliders
        robotColliders.add(newRobot.getSim());

        // add new robot to the scene
        world.getChildren().addAll(newRobot, newRobot.colliderRect);
        scrollPane.setContent(world);
    }

    /**
     * Adds new manual robot to the simulation
     *
     * @param x              Center X position to be set
     * @param y              Center Y position to be set
     * @param radius         Radius if this Robot
     * @param rot            Rotation of this robot
     * @param detRadius      Detection radius of this robot
     * @param color          Color of the AutoRobot
     * @param speed          Speed of the AutoRobot
     * @param spinClockwise  Boolean value whenever robot should spin direction
     * @param spinAnticlockwise  Boolean value whenever robot should spin direction
     * @param desiredAngle   Angle that robot will try to rotate to
     */
    public void addManualRobot(double x, double y, double radius, double rot,
                               double detRadius, Color color, double speed,
                               double turnAngle, boolean spinClockwise,
                               boolean spinAnticlockwise, double desiredAngle)
    {
        // create new robot, 10, 45, 1 = random default values
        ManualRobot newRobot = ManualRobot.create(x, y, radius, rot,
                detRadius, color, speed, turnAngle, 1,
                spinClockwise, spinAnticlockwise, desiredAngle,
                colliders, robotColliders, this);

        // add it to array of BaseRobots
        robots.add(newRobot);
        // add it to array of robot colliders
        robotColliders.add(newRobot.getSim());

        // add new robot to the scene
        world.getChildren().addAll(newRobot, newRobot.colliderRect);
        scrollPane.setContent(world);
    }


    /**
     * Adds new obstacle to the simulation
     *
     * @param x              Center X position to be set
     * @param y              Center Y position to be set
     * @param w              Width of obstacle
     * @param h              Height of obstacle
     * @param rot            Rotation of this robot
     * @param color          Color of the AutoRobot
     */
    public void addObstacle(double x, double y, double w, double h, double rot,
                                  Color color)
    {
        Obstacle newObstacle = Obstacle.create(x, y, w, h, rot, color, this);
        obstacles.add(newObstacle);
        colliders.add(newObstacle.getSim());

        world.getChildren().add(newObstacle);
        scrollPane.setContent(world);
    }

    /**
     * Deletes active object from the simulation
     */
    public void deleteObstacle()
    {
        if(activeObstacle != null)
        {
            world.getChildren().removeAll(activeObstacle);

            obstacles.remove(activeObstacle);
            activeObstacle = null;
        }
    }

    /**
     * Deletes active robot from the simulation
     */
    public void deleteRobot()
    {
        if(activeRobot != null)
        {
            world.getChildren().removeAll(activeRobot, activeRobot.colliderRect);

            robots.remove(activeRobot);
            activeRobot = null;
        }
    }

    /**
     * Selects obstacle object to be selected and active
     * @param obstacle Obstacle object to be selected
     */
    public void setActiveObstacle(Obstacle obstacle)
    {
        if(activeObstacle != null)
        {
            activeObstacle.unselectObstacle();
        }

        activeObstacle = obstacle;
        activeObstacle.selectObstacle();
    }

    /**
     * Selects robot object to be selected and active
     * @param robot BaseRobot object to be selected
     */
    public void setActiveRobot(BaseRobot robot)
    {
        if(activeRobot == robot)
        {
            return;
        }
        if(activeRobot != null)
        {
            activeRobot.unselectRobot();
        }

        if(robot instanceof  ManualRobot)
        {
            guiController.setManualRobotParams((ManualRobot) robot);
        }

        activeRobot = robot;
        activeRobot.selectRobot();
    }

    /**
     * @return Returns array of the obstacles
     */
    public ArrayList<Obstacle> getObstacles() { return obstacles; }

    /**
     * @return Returns array of robots
     */
    public ArrayList<BaseRobot> getRobots() { return robots; }

    /**
     * @return Returns this scroll pane
     */
    public ScrollPane getScrollPane() { return scrollPane; }

    /**
     * Pauses simulation
     */
    public void pauseSimulation()
    {
        running = false;
        this.stop();
    }

    /**
     * Resumes/unpauses simulation
     */
    public void resumeSimulation()
    {
        running = true;
        this.start();
    }

    /**
     * @return Returns true if simulator is not running
     */
    public boolean isPaused()
    {
        return !running;
    }

    /**
     * @return Returns true if simulator is running
     */
    public boolean isRunnning()
    {
        return running;
    }

    /**
     * @return Returns active robot if it is manual robot
     */
    public ManualRobot getActiveManualRobot()
    {
        if(activeRobot != null)
        {
            if(activeRobot instanceof ManualRobot)
            {
                return (ManualRobot) activeRobot;
            }
        }

        return  null;
    }

    /**
     * Sets size of simulation space
     * @param width Width of simulation space
     * @param height Height of simulation space
     */
    public void setSize(double width, double height)
    {
        this.spaceWidth = width;
        this.spaceHeight = height;
    }

    /**
     * @return Returns simulation space width
     */
    public double getSpaceWidth() {
        return spaceWidth;
    }

    /**
     * @return Returns simulation space width
     */
    public double getSpaceHeight() {
        return spaceHeight;
    }
}
