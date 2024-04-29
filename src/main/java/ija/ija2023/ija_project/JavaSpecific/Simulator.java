package ija.ija2023.ija_project.JavaSpecific;

import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import ija.ija2023.ija_project.SimulationLib2D.*;

/**
 *
 * @author Diony
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
     * Array of AutoRobots
     */
    ArrayList<AutoRobot> autoRobots;

    /**
     * Array of ManualRobots
     */
    ArrayList<ManualRobot> manualRobots;

    /**
     * Width of simulation space
     */
    int spaceWidth;

    /**
     * Height of simulation space
     */
    int spaceHeight;

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
     * Pointer to the Pane where objects will get stored
     */
    Pane root;
    public Simulator(Pane root, int width, int height)
    {
        obstacles = new ArrayList<Obstacle>();
        colliders = new ArrayList<Rect>();

        robots = new ArrayList<BaseRobot>();
        autoRobots = new ArrayList<AutoRobot>();
        manualRobots = new ArrayList<ManualRobot>();

        robotColliders = new ArrayList<Robot>();

        activeRobot = null;
        activeObstacle = null;

        lastUpdate = 0;
        this.root = root;
        this.spaceWidth = width;
        this.spaceHeight = height;
    }

    public Simulator addObstacle(Obstacle obstacle)
    {
        obstacles.add(obstacle);
        return this;
    }

    public Simulator addRobot(AutoRobot robot)
    {
        robots.add(robot);
        return this;
    }

    @Override
    public void handle(long now) 
    {
        double deltaTime = (now - lastUpdate) / 1e9;
        simulationCycle(deltaTime);
        lastUpdate = now;
    }

    public void simulationCycle(double deltaTime)
    {
        for(BaseRobot robot : robots)
        {
            robot.simulate(deltaTime);
        }
    }

    public void addAutomaticRobot(double x, double y, double radius, double rot,
                                     double detRadius, Color color, double speed,
                                     double turnAngle, int turnDirection)
    {
        // create new robot
        AutoRobot newRobot = AutoRobot.create(x, y, radius, rot, detRadius, color, speed, turnAngle, turnDirection, colliders, robotColliders, this);
        // add it to array of AutoRobots
        autoRobots.add(newRobot);
        // add it to array of BaseRobots
        robots.add(newRobot);
        // add it to array of robot colliders
        robotColliders.add(newRobot.getSim());
        // add new robot to the scene

        root.getChildren().addAll(newRobot, newRobot.colliderRect);
    }

//    public void addManualRobot(double x, double y, double radius, double rot,
//                                     double detRadius, Color color, double speed,
//                                     double turnAngle, int turnDirection)
//    {
//        // create new robot
//        ManualRobot newRobot = ManualRobot.create(x, y, radius, rot, detRadius, color, obstacles, robotColliders, this);
//        // add it to array of AutoRobots
//        autoRobots.add(newRobot);
//        // add it to array of BaseRobots
//        robots.add(newRobot);
//        // add it to array of robot colliders
//        robotColliders.add(newRobot.getSim());
//        // add new robot to the scene
//        root.getChildren().add(newRobot);
//    }

  public void addObstacle(double x, double y, double w, double h, double rot,
                                  Color color)
    {
        Obstacle newObstacle = Obstacle.create(x, y, w, h, rot, color);
        obstacles.add(newObstacle);
        colliders.add(newObstacle.getSim());

        root.getChildren().add(newObstacle);
    }

    public ArrayList<Obstacle> getObstacles() {
        return obstacles;
    }

    public ArrayList<BaseRobot> getRobots() {
        return robots;
    }

    public Pane getRoot() {
        return root;
    }
}
