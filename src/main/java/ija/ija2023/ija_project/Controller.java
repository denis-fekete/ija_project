/**
 * Implementation of Controller class. Controller is class for implementing
 * methods of GUI elements
 *
 * @author Denis Fekete (xfeket01@fit.vutbr.cz)
 */

package ija.ija2023.ija_project;

import ija.ija2023.ija_project.JavaSpecific.*;
import ija.ija2023.ija_project.SimulationLib2D.Point;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    /**
     * Reference to the simulator
     */
    Simulator simulator;

    /**
     * Reference to the main window stage
     */
    Stage mainWindowStage;

    /**
     * SaveManger object for loading and saving data
     */
    SaveManager saveManager;


    @FXML
    private ScrollPane simulationSpace;


    // ------------------------------------------------------------------------
    // Other
    // ------------------------------------------------------------------------

    @FXML // fx:id="controlMenu"
    private Accordion controlMenu;

    @FXML
    private Button control_btn_resume_stop;

    @FXML
    void btn_revertSimulation()
    {
        if(simulator.isSimulatingForward())
        {
            simulator.reverseSimulation();
            history_btn_reverse.setText("Continue simulation from this point");
        }
        else
        {
            history_btn_reverse.setText("Reverse simulation");
            simulator.reverseSimulation();
            control_resume_stop();
        }
    }

    @FXML
    public  void control_resume_stop()
    {
        if(simulator.isRunnning())
        {
            simulator.pauseSimulation();

            // enable menus
            robotcreator_parentPane.setDisable(false);
            world_parentPane.setDisable(false);
            obstaclecreator_parentPane.setDisable(false);

            control_btn_resume_stop.setText("Resume");
        }
        else
        {
            simulator.resumeSimulation();

            // disable other menus
            robotcreator_parentPane.setDisable(true);
            world_parentPane.setDisable(true);
            obstaclecreator_parentPane.setDisable(true);

            control_btn_resume_stop.setText("Pause");
        }
    }


    public void setMainWindowStage(Stage mainWindowStage) {
        this.mainWindowStage = mainWindowStage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setRobotCreatorDefaultValues();
        setObstacleCreatorDefaultValues();
        setRobotMoveDefaultValues();
        setWorldDefaultValues();
    }

    private void setRobotCreatorDefaultValues()
    {
        robotcreator_type.getItems().addAll("Automatic", "Manual");
        robotcreator_rot_direction.getItems().addAll("Clockwise", "Anti-clockwise");

        robotcreator_rot_direction.setValue("Clockwise");
        robotcreator_type.setValue("Automatic");

        robotcreator_x_pos.getValueFactory().setValue(50.0);
        robotcreator_y_pos.getValueFactory().setValue(50.0);

        robotcreator_speed.getValueFactory().setValue(20.0);

        robotcreator_radius.getValueFactory().setValue(20.0);
        robotcreator_detection_radius.getValueFactory().setValue(50.0);

        robotcreator_rotation.getValueFactory().setValue(0.0);
        robotcreator_rot_speed.getValueFactory().setValue(30.0);
    }

    private void setObstacleCreatorDefaultValues()
    {
        obstaclecreator_x_pos.getValueFactory().setValue(100.0);
        obstaclecreator_y_pos.getValueFactory().setValue(100.0);

        obstaclecreator_height.getValueFactory().setValue(60.0);
        obstaclecreator_width.getValueFactory().setValue(60.0);

        obstaclecreator_rotation.getValueFactory().setValue(0.0);
    }

    private void setRobotMoveDefaultValues()
    {
        robotmove_speedmax.getValueFactory().setValue(300.0);
        robotmove_speedslider.setValue(0);
        robotmove_desired_angle.setValue(0);
        robotmove_rotation_speed.getValueFactory().setValue(45.0);
    }

    private  void setWorldDefaultValues()
    {
        // default windows size should be 800, 600 to be
        // correctly displayed even on older machines
        world_size_x.getValueFactory().setValue(800.0 - 240);
        world_size_y.getValueFactory().setValue(600.0);
    }

    public void setSimulator(Simulator simulator) {
        this.simulator = simulator;
    }

    public ScrollPane getSimulationPane() {
        return simulationSpace;
    }

    public void resizeContents(Number width, Number height)
    {
        if(width != null)
        {
            simulationSpace.setPrefWidth(width.doubleValue() - controlMenu.getPrefWidth());
        }

        if(height != null)
        {
            // 3*20 is size of collapsed panes in menu bar
            controlMenu.setPrefHeight(height.doubleValue() - control_btn_resume_stop.getPrefHeight()  - (3*13));
            simulationSpace.setPrefHeight(height.doubleValue());
        }
    }



    // ------------------------------------------------------------------------
    // Robot creator menu
    // ------------------------------------------------------------------------

    @FXML
    private ColorPicker robotcreator_color;

    @FXML 
    private Spinner<Double> robotcreator_rotation;

    @FXML 
    private Spinner<Double> robotcreator_detection_radius;

    @FXML
    private Spinner<Double> robotcreator_radius;

    @FXML
    private ChoiceBox<String> robotcreator_rot_direction;

    @FXML
    private Spinner<Double> robotcreator_rot_speed;

    @FXML
    private Spinner<Double> robotcreator_speed;

    @FXML
    private ChoiceBox<String> robotcreator_type;

    @FXML
    private Spinner<Double> robotcreator_x_pos;

    @FXML
    private Spinner<Double> robotcreator_y_pos;

    @FXML
    private AnchorPane robotcreator_parentPane;


    @FXML
    void btn_addRobot(ActionEvent event) {
        int turnDirection;
        if (robotcreator_rot_direction.getValue().equals("Clockwise")) {
            turnDirection = 1;
        } else {
            turnDirection = -1;
        }

        if (robotcreator_type.getValue().equals("Automatic"))
        {
            simulator.addAutomaticRobot(
                    robotcreator_x_pos.getValue(),
                    robotcreator_y_pos.getValue(),
                    robotcreator_radius.getValue(),
                    robotcreator_rotation.getValue(),
                    robotcreator_detection_radius.getValue(),
                    robotcreator_color.getValue(),
                    robotcreator_speed.getValue(),
                    robotcreator_rot_speed.getValue(),
                    turnDirection);
        }
        else
        {
            simulator.addManualRobot( robotcreator_x_pos.getValue(),
                    robotcreator_y_pos.getValue(),
                    robotcreator_radius.getValue(),
                    robotcreator_rotation.getValue(),
                    robotcreator_detection_radius.getValue(),
                    robotcreator_color.getValue());
        }
    }

    @FXML
    void btn_deleteRobot()
    {
        simulator.deleteRobot();
    }

    @FXML
    void btn_updateSelectedRobot()
    {
        BaseRobot robot = simulator.getActiveRobot();
        if(robot == null)
        {
            return;
        }

        int turnDirection;
        if (robotcreator_rot_direction.getValue().equals("Clockwise")) {
            turnDirection = 1;
        } else {
            turnDirection = -1;
        }

        robot.updateRobotValues(robotcreator_x_pos.getValue(), robotcreator_y_pos.getValue(),
                robotcreator_radius.getValue(), robotcreator_rotation.getValue(),
                robotcreator_detection_radius.getValue(), robotcreator_speed.getValue(),
                robotcreator_rot_speed.getValue(),
                turnDirection);

        if(robot instanceof  AutoRobot)
            robot.addLog(CommandType.SAVE_AUTO, simulator.getLogId());
        else
            robot.addLog(CommandType.SAVE_MANUAL, simulator.getLogId());
    }

    @FXML
    public void setRobotCreatorParams(BaseRobot robot)
    {
        if(robot == null)
        {
            return;
        }

        if(robot.getTurnDirection() == 1)
            robotcreator_rot_direction.setValue("Clockwise");
        else
            robotcreator_rot_direction.setValue("Anti-clockwise");

        if(robot instanceof AutoRobot)
            robotcreator_type.setValue("Automatic");
        else
            robotcreator_type.setValue("Manual");

        robotcreator_x_pos.getValueFactory().setValue(robot.getSim().getX());
        robotcreator_y_pos.getValueFactory().setValue(robot.getSim().getY());

        robotcreator_speed.getValueFactory().setValue(robot.getSpeed());

        robotcreator_radius.getValueFactory().setValue(robot.getSim().getRadius());
        robotcreator_detection_radius.getValueFactory().setValue(robot.getSim().getDetRadius());

        robotcreator_rotation.getValueFactory().setValue(robot.getSim().getRotation());
        robotcreator_rot_speed.getValueFactory().setValue(robot.getTurnSpeed());
    }



    // ------------------------------------------------------------------------
    // Obstacle creator menu
    // ------------------------------------------------------------------------

    @FXML
    private Button obstaclecreator_btn_create;

    @FXML
    private ColorPicker obstaclecreator_color; 

    @FXML
    private Spinner<Double> obstaclecreator_height; 

    @FXML
    private Spinner<Double> obstaclecreator_rotation; 

    @FXML
    private Spinner<Double> obstaclecreator_width; 

    @FXML
    private Spinner<Double> obstaclecreator_x_pos; 

    @FXML
    private Spinner<Double> obstaclecreator_y_pos; 

    @FXML
    private AnchorPane obstaclecreator_parentPane;

    @FXML
    void btn_addObstacle(ActionEvent event) {
        simulator.addObstacle(
                obstaclecreator_x_pos.getValue(),
                obstaclecreator_y_pos.getValue(),
                obstaclecreator_width.getValue(),
                obstaclecreator_height.getValue(),
                obstaclecreator_rotation.getValue(),
                obstaclecreator_color.getValue()
        );
    }

    @FXML
    void btn_deleteObstacle()
    {
        simulator.deleteObstacle();
    }

    @FXML
    public void setObstacleCreatorParams(Obstacle obstacle)
    {
        if(obstacle == null)
        {
            return;
        }

        obstaclecreator_x_pos.getValueFactory().setValue(obstacle.getSim().getX());
        obstaclecreator_y_pos.getValueFactory().setValue(obstacle.getSim().getY());

        obstaclecreator_width.getValueFactory().setValue(obstacle.getSim().getWidth());
        obstaclecreator_height.getValueFactory().setValue(obstacle.getSim().getHeight());

        obstaclecreator_rotation.getValueFactory().setValue(obstacle.getSim().getRotation());
    }

    @FXML
    public void btn_updateSelectedObstacle()
    {
        Obstacle obstacle = simulator.getActiveObstacle();
        if(obstacle == null)
        {
            return;
        }

        obstacle.moveObstacleTo(new Point(obstaclecreator_x_pos.getValue(), obstaclecreator_y_pos.getValue()));
        obstacle.updateObstacleValues(obstaclecreator_x_pos.getValue(), obstaclecreator_y_pos.getValue(),
                obstaclecreator_rotation.getValue(), obstaclecreator_width.getValue(),
                obstaclecreator_height.getValue());

    }



    // ------------------------------------------------------------------------
    // Robot move menu
    // ------------------------------------------------------------------------

    @FXML
    private Slider robotmove_desired_angle; 

    @FXML
    private ToggleButton robotmove_spin_anticlockwise; 

    @FXML
    private ToggleButton robotmove_spin_clockwise; 

    @FXML
    private Spinner<Double> robotmove_rotation_speed; 

    @FXML
    private Spinner<Double> robotmove_speedmax; 

    @FXML
    private Slider robotmove_speedslider; 

    public void setManualRobotParams(ManualRobot robot)
    {
        robotmove_speedslider.setValue(robot.getSpeed());
        robotmove_desired_angle.setValue(robot.getDesiredAngle());

        // if robot doesn't have turn speed, copy current, else set value that the robot has
        if(robot.getTurnSpeed() == 0)
        {
            robot.setTurnSpeed(robotmove_rotation_speed.getValue());
        }
        else
        {
            robotmove_rotation_speed.getValueFactory().setValue(robot.getTurnSpeed());
        }

        robotmove_spin_clockwise.setSelected(robot.isSpinClockwise());
        robotmove_spin_anticlockwise.setSelected(robot.isSpinAnticlockwise());
    }

    @FXML
    void robotmove_angle_changed() {
        ManualRobot robot =  simulator.getActiveManualRobot();
        if(robot == null)
        {
            return;
        }

        robot.setDesiredAngle(robotmove_desired_angle.getValue());
        robot.addLog(CommandType.SAVE_MANUAL, simulator.getLogId());
    }

    @FXML
    void robotmove_btn_spin_clockwise(ActionEvent event) {
        ManualRobot robot = simulator.getActiveManualRobot();
        if(robot == null)
        {
            robotmove_spin_anticlockwise.setSelected(false);
            robotmove_spin_clockwise.setSelected(false);
            return;
        }

        if(robotmove_spin_clockwise.isSelected())
        {
            robotmove_spin_anticlockwise.setSelected(false);
            robot.setSpinAnticlockwise(false);
            robot.setSpinClockwise(true);
        }
        else
        {
            robot.setSpinClockwise(false);
            robot.setDesiredAngle(robot.getSim().getRotation());
        }
        robot.addLog(CommandType.SAVE_MANUAL, simulator.getLogId());
    }

    @FXML
    void robotmove_btn_spin_anticlockwise(ActionEvent event) {
        ManualRobot robot = simulator.getActiveManualRobot();
        if(robot == null)
        {
            robotmove_spin_anticlockwise.setSelected(false);
            robotmove_spin_clockwise.setSelected(false);
            return;
        }

        if(robotmove_spin_anticlockwise.isSelected())
        {
            robotmove_spin_clockwise.setSelected(false);
            robot.setSpinAnticlockwise(true);
            robot.setSpinClockwise(false);

        }
        else
        {
            robot.setSpinAnticlockwise(false);
            robot.setDesiredAngle(robot.getSim().getRotation());
        }
        robot.addLog(CommandType.SAVE_MANUAL, simulator.getLogId());
    }

    @FXML
    void robotmove_rot_speed_changed() {
        ManualRobot robot = simulator.getActiveManualRobot();
        if(robot == null)
        {
            return;
        }

        robot.setTurnSpeed(robotmove_rotation_speed.getValue());
        robot.addLog(CommandType.SAVE_MANUAL, simulator.getLogId());
    }

    @FXML
    void robotmove_speed_changed() {
        ManualRobot robot = simulator.getActiveManualRobot();
        if(robot == null)
        {
            return;
        }

        robot.setSpeed(robotmove_speedslider.getValue());
        robot.addLog(CommandType.SAVE_MANUAL, simulator.getLogId());
    }

    @FXML
    void robotmove_max_speed_changed() {
        // if new speed limit is lower than actual robot speed
        if(robotmove_speedmax.getValueFactory().getValue() < robotmove_speedslider.getValue())
        {
            // set new speed
            simulator.getActiveManualRobot().setSpeed(robotmove_speedmax.getValueFactory().getValue());
            robotmove_speedslider.setValue(robotmove_speedmax.getValueFactory().getValue());
        }

        robotmove_speedslider.setMax(robotmove_speedmax.getValue());
        robotmove_speedslider.setMajorTickUnit(robotmove_speedmax.getValueFactory().getValue() / 10);
        simulator.getActiveManualRobot().addLog(CommandType.SAVE_MANUAL, simulator.getLogId());
    }

    // ------------------------------------------------------------------------
    // World menu
    // ------------------------------------------------------------------------


    @FXML
    private Spinner<Double> world_size_x; 

    @FXML
    private Spinner<Double> world_size_y; 

    @FXML
    private AnchorPane world_parentPane;

    @FXML
    private Button history_btn_reverse;

    @FXML
    public void world_apply_world_border()
    {
        simulator.setSize(world_size_x.getValueFactory().getValue(), world_size_y.getValueFactory().getValue());
    }

    @FXML
    void world_load_file()
    {
        if(saveManager == null)
        {
            saveManager = new SaveManager(simulator, mainWindowStage);
        }

        try
        {
            saveManager.loadFromFile();
        }
        catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Failed to load file");
            alert.setContentText("Exception occurred while loading from input XML file.");
            alert.showAndWait();
        }
    }

    @FXML
    void world_write_file()
    {
        if(saveManager == null)
        {
            saveManager = new SaveManager(simulator, mainWindowStage);
        }

        try
        {
            saveManager.saveToFile();
        }
        catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Failed to load file");
            alert.setContentText("Exception occurred while writing to output XML file.");
            alert.showAndWait();
        }
    }
}
