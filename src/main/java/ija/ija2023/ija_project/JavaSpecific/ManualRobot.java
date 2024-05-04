/**
 * Implementation of BaseRobot class. ManualRobot is specialization of BaseRobot that can only be controlled by
 * user inputs (through GUI)
 *
 * @author Denis Fekete (xfeket01@fit.vutbr.cz)
 */

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
     * @param spinClockwise  Boolean value whenever robot should spin direction
     * @param spinAnticlockwise  Boolean value whenever robot should spin direction
     * @param desiredAngle   Angle that robot will try to rotate to
     * @param obstacles      Vector of obstacles
     * @param robotColliders Vector of all robots
     * @param simulator      Simulator object
     */
    private ManualRobot(double x, double y, double radius, double rot,
                      double detRadius, Color color, double speed,
                      double turnSpeed, int turnDirection,
                      boolean spinClockwise, boolean spinAnticlockwise,
                      double desiredAngle,
                      ArrayList<Rect> obstacles,
                      ArrayList<Robot> robotColliders,
                      Simulator simulator)
    {
        super(x, y, radius, rot, detRadius, color, speed, turnSpeed, turnDirection, obstacles, robotColliders, simulator);
        this.spinClockwise = spinClockwise;
        this.spinAnticlockwise = spinAnticlockwise;
        this.desiredAngle = desiredAngle;
    }

    /**
     * Creates an instance of ManualRobot, instantiates it and returns it
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
     * @param spinClockwise  Boolean value whenever robot should spin direction
     * @param spinAnticlockwise  Boolean value whenever robot should spin direction
     * @param desiredAngle   Angle that robot will try to rotate to
     * @param obstacles      Vector of obstacles
     * @param robotColliders Vector of all robots
     * @param simulator      Simulator object
     * @return Created object
     */
    public static ManualRobot create(double x, double y, double radius, double rot,
                     double detRadius, Color color, double speed,
                     double turnSpeed, int turnDirection,
                     boolean spinClockwise, boolean spinAnticlockwise,
                     double desiredAngle,
                     ArrayList<Rect> obstacles,
                     ArrayList<Robot> robotColliders,
                     Simulator simulator)
    {
        ManualRobot newRobot = new ManualRobot(x, y, radius, rot, detRadius,
                color, speed, turnSpeed, turnDirection,
                spinClockwise, spinAnticlockwise, desiredAngle,
                obstacles, robotColliders, simulator);

        newRobot.initialize();

        newRobot.setOnMousePressed(mouseEvent -> {
            if(simulator.isPaused() && simulator.isSimulatingForward())
            {
                newRobot.addLog(CommandType.SAVE_MANUAL, simulator.getLogId());
            }
        });

        return newRobot;
    }

    /**
     * One simulation cycle
     * @param logId Log ID for logging of commands
     */
    public void simulate(int logId)
    {
        if(spinClockwise)
        {
            rotateRobot( turnSpeed * SMOOTH_CONST);
        }
        else if(spinAnticlockwise)
        {
            rotateRobot(-turnSpeed * SMOOTH_CONST);
        }
        else if(desiredAngle > this.sim.getRotation() )
        {
            rotateRobot( turnSpeed * SMOOTH_CONST);
        }
        else if(desiredAngle < this.sim.getRotation())
        {
            rotateRobot(-turnSpeed * SMOOTH_CONST);
        }
        else if(speed > 0)
        {
            if(!sim.obstacleDetection(colliders) && !sim.robotDetection(robotColliders) && !isOutside())
            {
                moveRobot(speed * SMOOTH_CONST);
            }
        }
    }

    /**
     * Sets parameters from Command into current Robot
     * @param cmd Command from which values will be taken from
     */
    public void setParameters(ManualRobotPositionSaveCmd cmd)
    {
        super.setParameters(cmd);

        this.spinClockwise = cmd.spinClockwise;
        this.spinAnticlockwise = cmd.spinAnticlockwise;
        this.desiredAngle = cmd.desiredAngle;

        rotateRobot(0);
        moveRobotTo(sim.getPos());
    }

    /**
     * Method that gets called instead of simulate() when simulation is in
     * reverse mode
     * @param logId Log id that should "reverse" simulate
     */
    public void reverseSimulate(int logId)
    {
        if(lastLogIndex < 0)
            return;

        // check if logId parameter is same as lastLogIndex,
        // if yes set last to logId
        while(log.get(lastLogIndex).logId == logId)
        {
            if(log.get(lastLogIndex).getType() == CommandType.SAVE_MANUAL)
            {
                setParameters((ManualRobotPositionSaveCmd) log.get(lastLogIndex));
                simulator.guiController.setManualRobotParams(this);
            }

            log.remove(lastLogIndex);
            lastLogIndex--;

            if(lastLogIndex < 0)
                return;
        }
    }

    /**
     * Set field of robot to spin in clockwise direction
     * @param spinClockwise Value to be set
     */
    public void setSpinClockwise(boolean spinClockwise) {
        this.spinClockwise = spinClockwise;
    }

    /**
     * Set field of robot to spin in anticlockwise direction
     * @param spinAnticlockwise Value to be set
     */
    public void setSpinAnticlockwise(boolean spinAnticlockwise) {
        this.spinAnticlockwise = spinAnticlockwise;
    }

    /**
     * @return Value of spinClockwise flied in robot
     */
    public boolean isSpinClockwise() {
        return spinClockwise;
    }

    /**
     * @return Value of spinAnticlockwise flied in robot
     */
    public boolean isSpinAnticlockwise() {
        return spinAnticlockwise;
    }

    /**
     * Sets new angle that robot will try to get to
     * @param desiredAngle New value to be set
     */
    public void setDesiredAngle(double desiredAngle) {
        this.desiredAngle = desiredAngle;
    }

    /**
     * @return Desired angle of the robot
     */
    public double getDesiredAngle() {
        return desiredAngle;
    }
}

