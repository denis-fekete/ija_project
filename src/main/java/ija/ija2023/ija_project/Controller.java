package ija.ija2023.ija_project;

import  ija.ija2023.ija_project.JavaSpecific.Simulator;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Spinner;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    Simulator simulator;

    @FXML // fx:id="simulationSpace"
    private ScrollPane simulationSpace; // Value injected by FXMLLoader

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

    @FXML // fx:id="robotcreator_btn_create"
    private Button robotcreator_btn_create; // Value injected by FXMLLoader

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setRobotCreatorDefaultValues();
        setObstacleCreatorDefaultValues();
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
        robotcreator_rot_speed.getValueFactory().setValue(15.0);
    }

    private void setObstacleCreatorDefaultValues()
    {
        obstaclecreator_x_pos.getValueFactory().setValue(100.0);
        obstaclecreator_y_pos.getValueFactory().setValue(100.0);

        obstaclecreator_height.getValueFactory().setValue(60.0);
        obstaclecreator_width.getValueFactory().setValue(60.0);

        obstaclecreator_rotation.getValueFactory().setValue(0.0);
    }



    public void setSimulator(Simulator simulator) {
        this.simulator = simulator;
    }

    public ScrollPane getSimulationPane() {
        return simulationSpace;
    }

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
    void btn_addRobot(ActionEvent event) {
        int turnDirection;
        if (robotcreator_rot_direction.getValue().equals("Clockwise")) {
            turnDirection = 1;
        } else {
            turnDirection = -1;
        }

        if (robotcreator_type.getValue().equals("Automatic")) {
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
    }

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

    @FXML
    void btn_deleteRobot()
    {
        simulator.deleteRobot();
    }

    @FXML
    void btn_deleteObstacle()
    {
        simulator.deleteObstacle();
    }
}
