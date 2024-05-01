package ija.ija2023.ija_project;

import ija.ija2023.ija_project.JavaSpecific.ManualRobot;
import ija.ija2023.ija_project.JavaSpecific.SaveManager;
import ija.ija2023.ija_project.JavaSpecific.Simulator;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller is class for implementing methods of GUI elements
 *
 * @author Denis Fekete (xfeket01@fit.vutbr.cz)
 */
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
    private HBox timeMenu;

    @FXML // fx:id="controlMenu"
    private Accordion controlMenu;

    @FXML // fx:id="simulationSpace"
    private ScrollPane simulationSpace; // Value injected by FXMLLoader

    // ------------------------------------------------------------------------

    @FXML // fx:id="obstaclecreator_btn_create"
    private Button obstaclecreator_btn_create; // Value injected by FXMLLoader

    @FXML // fx:id="obstaclecreator_color"
    private ColorPicker obstaclecreator_color; // Value injected by FXMLLoader

    @FXML // fx:id="obstaclecreator_height"
    private Spinner<Double> obstaclecreator_height; // Value injected by FXMLLoader

    @FXML // fx:id="obstaclecreator_rotation"
    private Spinner<Double> obstaclecreator_rotation; // Value injected by FXMLLoader

    @FXML // fx:id="obstaclecreator_width"
    private Spinner<Double> obstaclecreator_width; // Value injected by FXMLLoader

    @FXML // fx:id="obstaclecreator_x_pos"
    private Spinner<Double> obstaclecreator_x_pos; // Value injected by FXMLLoader

    @FXML // fx:id="obstaclecreator_y_pos"
    private Spinner<Double> obstaclecreator_y_pos; // Value injected by FXMLLoader

    // ------------------------------------------------------------------------

    @FXML // fx:id="robotcreator_color"
    private ColorPicker robotcreator_color; // Value injected by FXMLLoader

    @FXML // fx:id="robotcreator_default_rot"
    private Spinner<Double> robotcreator_rotation; // Value injected by FXMLLoader

    @FXML // fx:id="robotcreator_detection_radius"
    private Spinner<Double> robotcreator_detection_radius; // Value injected by FXMLLoader

    @FXML // fx:id="robotcreator_radius"
    private Spinner<Double> robotcreator_radius; // Value injected by FXMLLoader

    @FXML // fx:id="robotcreator_rot_direction"
    private ChoiceBox<String> robotcreator_rot_direction; // Value injected by FXMLLoader

    @FXML // fx:id="robotcreator_rot_speed"
    private Spinner<Double> robotcreator_rot_speed; // Value injected by FXMLLoader

    @FXML // fx:id="robotcreator_speed"
    private Spinner<Double> robotcreator_speed; // Value injected by FXMLLoader

    @FXML // fx:id="robotcreator_type"
    private ChoiceBox<String> robotcreator_type; // Value injected by FXMLLoader

    @FXML // fx:id="robotcreator_x_pos"
    private Spinner<Double> robotcreator_x_pos; // Value injected by FXMLLoader

    @FXML // fx:id="robotcreator_y_pos"
    private Spinner<Double> robotcreator_y_pos; // Value injected by FXMLLoader

    // ------------------------------------------------------------------------

    @FXML // fx:id="robotmove_desired_angle"
    private Slider robotmove_desired_angle; // Value injected by FXMLLoader

    @FXML // fx:id="robotmove_spin_anticlockwise"
    private ToggleButton robotmove_spin_anticlockwise; // Value injected by FXMLLoader

    @FXML // fx:id="robotmove_spin_clockwise"
    private ToggleButton robotmove_spin_clockwise; // Value injected by FXMLLoader

    @FXML // fx:id="robotmove_rotation_speed"
    private Spinner<Double> robotmove_rotation_speed; // Value injected by FXMLLoader

    @FXML // fx:id="robotmove_speedmax"
    private Spinner<Double> robotmove_speedmax; // Value injected by FXMLLoader

    @FXML // fx:id="robotmove_speedslider"
    private Slider robotmove_speedslider; // Value injected by FXMLLoader

    @FXML // fx:id="world_size_x"
    private Spinner<Double> world_size_x; // Value injected by FXMLLoader

    @FXML // fx:id="world_size_y"
    private Spinner<Double> world_size_y; // Value injected by FXMLLoader


    // ------------------------------------------------------------------------


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
            controlMenu.setPrefHeight(height.doubleValue() - timeMenu.getPrefHeight()  - (3*13));
            simulationSpace.setPrefHeight(height.doubleValue());
        }
    }

    // ------------------------------------------------------------------------

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

    // ------------------------------------------------------------------------

    @FXML
    void btn_revertSimulation()
    {

    }

    @FXML
    void btn_stopSimulation()
    {
        simulator.pauseSimulation();
    }

    @FXML
    void btn_startSimulation()
    {
        simulator.resumeSimulation();
    }

    // ------------------------------------------------------------------------

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

    // ------------------------------------------------------------------------

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

    }

    @FXML
    void robotmove_rot_speed_changed() {
        ManualRobot robot = simulator.getActiveManualRobot();
        if(robot == null)
        {
            return;
        }

        robot.setTurnSpeed(robotmove_rotation_speed.getValue());
    }

    @FXML
    void robotmove_speed_changed() {
        ManualRobot robot = simulator.getActiveManualRobot();
        if(robot == null)
        {
            return;
        }

        robot.setSpeed(robotmove_speedslider.getValue());
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
    }

    // ------------------------------------------------------------------------

    @FXML
    public void world_apply_world_border()
    {
        simulator.setSize(world_size_x.getValue(), world_size_y.getValue());
        simulationSpace.setPrefWidth(world_size_x.getValue());
        simulationSpace.setPrefHeight(world_size_y.getValue());
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
