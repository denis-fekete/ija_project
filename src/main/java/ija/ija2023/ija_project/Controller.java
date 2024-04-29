package ija.ija2023.ija_project;

import  ija.ija2023.ija_project.Main;
import  ija.ija2023.ija_project.JavaSpecific.Simulator;

import javafx.fxml.FXML;
import javafx.scene.paint.Color;

public class Controller {
    Simulator simulator;

    public void setSimulator(Simulator simulator) {
        this.simulator = simulator;
    }

    @FXML
    private void btn_addObstacle()
    {
        simulator.addObstacle(100, 100, 100, 100, 0, Color.RED);
    }
}