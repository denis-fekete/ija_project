package ija.ija2023.ija_project.JavaSpecific;

import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
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
     * Pane where robots and obstacles are stored
     */
    Pane world;

    /**
     * Scroll pane that will be displayed
     */
    ScrollPane scrollPane;

    /**
     * Boolean value telling if the Simulator is pause or not
     */
    boolean running;
    public Simulator(int width, int height, ScrollPane scrollPane)
    {
        obstacles = new ArrayList<Obstacle>();
        colliders = new ArrayList<Rect>();

        robots = new ArrayList<BaseRobot>();

        robotColliders = new ArrayList<Robot>();

        activeRobot = null;
        activeObstacle = null;

        this.world = new Pane();

        this.scrollPane = scrollPane;

        lastUpdate = 0;
        this.spaceWidth = width;
        this.spaceHeight = height;

        running = false;
    }

    @Override
    public void handle(long now) 
    {
        double deltaTime = (now - lastUpdate) / 1e9;
        if(running)
        {
            simulationCycle(deltaTime);
        }
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
        // add it to array of BaseRobots
        robots.add(newRobot);
        // add it to array of robot colliders
        robotColliders.add(newRobot.getSim());
        // add new robot to the scene

        world.getChildren().addAll(newRobot, newRobot.colliderRect);
        scrollPane.setContent(world);
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
        Obstacle newObstacle = Obstacle.create(x, y, w, h, rot, color, this);
        obstacles.add(newObstacle);
        colliders.add(newObstacle.getSim());

        world.getChildren().add(newObstacle);
        scrollPane.setContent(world);
    }

    public void deleteObstacle()
    {
        if(activeObstacle != null)
        {
            world.getChildren().removeAll(activeObstacle);

            obstacles.remove(activeObstacle);
            activeObstacle = null;
        }
    }

    public void deleteRobot()
    {
        if(activeRobot != null)
        {
            world.getChildren().removeAll(activeRobot, activeRobot.colliderRect);

            robots.remove(activeRobot);
            activeRobot = null;
        }
    }

    public void setActiveObstacle(Obstacle obstacle)
    {
        if(activeObstacle != null)
        {
            activeObstacle.unselectObstacle();
        }

        activeObstacle = obstacle;
        activeObstacle.selectObstacle();
    }

    public void setActiveRobot(BaseRobot robot)
    {
        if(activeRobot != null)
        {
            activeRobot.unselectRobot();
        }

        activeRobot = robot;
        activeRobot.selectRobot();
    }
    public ArrayList<Obstacle> getObstacles() {
        return obstacles;
    }

    public ArrayList<BaseRobot> getRobots() {
        return robots;
    }

    public Pane getWorldPane() {
        return world;
    }

    public ScrollPane getScrollPane()
    {
        return scrollPane;
    }

    public void pauseSimulation()
    {
        running = false;
//        this.stop();
    }

    public void resumeSimulation()
    {
        running = true;
//        this.start();
    }

    public boolean isPaused()
    {
        return !running;
    }

    public  boolean isRunnning()
    {
        return running;
    }
}
