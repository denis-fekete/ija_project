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

    /**
     * Array of obstacles that robot can collide with
     */
    ArrayList<Rect> colliders;

    /**
     * Array of other robots that robot can collide with
     */
    ArrayList<Robot> robotColliders;

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
     * @param obstacles      Pointer to the vector of obstacles
     * @param robotColliders Pointer to the vector of all robots
     * @param simulator      Pointer to the simulator
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
    }

    public void initialize()
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
            if(simulator.isPaused())
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

    public void rotateRobot(double angle)
    {
        sim.rotate(angle);
        this.setRotate(sim.getRotation());

        this.colliderRect.setX(sim.colliderFwd.getX() - sim.colliderFwd.getWidth() / 2);
        this.colliderRect.setY(sim.colliderFwd.getY() - sim.colliderFwd.getHeight() / 2);
        this.colliderRect.setRotate(sim.colliderFwd.getRotation());
    }

    public void moveRobot(double distance)
    {
        sim.moveForward(distance);

        this.setCenterX(sim.getX());
        this.setCenterY(sim.getY());

        this.colliderRect.setX(sim.colliderFwd.getX() - sim.colliderFwd.getWidth() / 2);
        this.colliderRect.setY(sim.colliderFwd.getY() - sim.colliderFwd.getHeight() / 2);
    }

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
     * Simulates one simulation cycle of robot
     */
    public void simulate(double deltaTime) {}

    public Robot getSim() {
        return sim;
    }

    public void selectRobot()
    {
        this.setStroke(highlightedColor);
        this.setStrokeWidth(5);
    }

    public void unselectRobot()
    {
        this.setStroke(color);
        this.setStrokeWidth(1);
    }

    public double getSpeed()
    {
        return this.speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getTurnSpeed() {
        return turnSpeed;
    }

    public void setTurnSpeed(double turnSpeed) {
        this.turnSpeed = turnSpeed;
    }

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
}
