package ija.ija2023.ija_project.JavaSpecific;

import ija.ija2023.ija_project.SimulationLib2D.Rect;
import ija.ija2023.ija_project.SimulationLib2D.Robot;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class ManualRobot extends BaseRobot {
    /**
     * If set to true robot will spin clockwise direction
     */
    boolean spinClockwise;

    /**
     * If set to true robot will spin in anticlockwise direction
     */
    boolean spinAnticlockwise;

    /**
     * Turn that robot will try get rotate to by turnSpeed (field in BaseRobot)
     */
    double desiredAngle;

    private ManualRobot(double x, double y, double radius, double rot,
                      double detRadius, Color color, double speed,
                      double turnAngle, int turnDirection,
                      ArrayList<Rect> obstacles,
                      ArrayList<Robot> robotColliders,
                      Simulator simulator)
    {
        super(x, y, radius, rot, detRadius, color, speed, turnAngle, turnDirection, obstacles, robotColliders, simulator);
        spinClockwise = false;
        spinAnticlockwise = false;
        desiredAngle = rot;
    }

    public static ManualRobot create(double x, double y, double radius, double rot,
                     double detRadius, Color color, double speed,
                     double turnAngle, int turnDirection,
                     ArrayList<Rect> obstacles,
                     ArrayList<Robot> robotColliders,
                     Simulator simulator)
    {
        ManualRobot newRobot = new ManualRobot(x, y, radius, rot, detRadius, color, speed, turnAngle, turnDirection, obstacles, robotColliders, simulator);
        newRobot.initialize();

        return newRobot;
    }

    public void simulate(double deltaTime)
    {
        if(spinClockwise)
        {
            rotateRobot(turnSpeed * deltaTime);
        }
        else if(spinAnticlockwise)
        {
            rotateRobot(-turnSpeed * deltaTime);
        }
        else if(desiredAngle > this.sim.getRotation() )
        {
            rotateRobot(turnSpeed * deltaTime);
        }
        else if(desiredAngle < this.sim.getRotation())
        {
            rotateRobot(-turnSpeed * deltaTime);
        }

        if(speed > 0)
        {
            if(!sim.obstacleDetection(colliders) && !sim.robotDetection(robotColliders) && !isOutside())
            {
                moveRobot(speed * deltaTime);
            }
        }
    }

    public void setSpinClockwise(boolean spinClockwise) {
        this.spinClockwise = spinClockwise;
    }

    public void setSpinAnticlockwise(boolean spinAnticlockwise) {
        this.spinAnticlockwise = spinAnticlockwise;
    }

    public boolean isSpinClockwise() {
        return spinClockwise;
    }

    public boolean isSpinAnticlockwise() {
        return spinAnticlockwise;
    }

    public void setDesiredAngle(double desiredAngle) {
        this.desiredAngle = desiredAngle;
    }

    public double getDesiredAngle() {
        return desiredAngle;
    }


}

