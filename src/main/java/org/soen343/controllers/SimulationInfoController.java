package org.soen343.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import org.soen343.services.SimulationInfoService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SimulationInfoController extends Controller {

    SimulationInfoService simulationInfoService;
    @FXML
    private ToggleButton startStopToggle;
    @FXML
    private Label profileName;
    @FXML
    private Label role;
    @FXML
    private Label chosenLocation;
    @FXML
    private Label outsideTemp;
    @FXML
    private Label chosenDate;
    @FXML
    private Label chosenTime;

    public void initializeController() {
        simulationInfoService = new SimulationInfoService();
        update();
    }

    @FXML
    private void startSimulation(ActionEvent actionEvent) {
        boolean success = simulationInfoService.setSimulationRunning();
        boolean status = simulationInfoService.getSimulationStatus();
        startStopToggle.setText(status ? "ON" : "OFF");
        startStopToggle.setSelected(status);
    }

    public void openSimulationContextView(ActionEvent actionEvent) {
        mainController.enterSimulationContext();
    }

    public void update() {
        profileName.setText(simulationInfoService.getIndividualName());
        role.setText(simulationInfoService.getIndividualRole());
        chosenLocation.setText(simulationInfoService.getIndividualLocation());

        // Format Date and Time
        LocalDate date = simulationInfoService.getContextDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        String formattedDate = date.format(formatter);
        String hours = Integer.toString(simulationInfoService.getContextHours());
        String min = Integer.toString(simulationInfoService.getContextMin());
        String sec = Integer.toString(simulationInfoService.getContextSec());
        hours = hours.length() == 1 ? "0" + hours : hours;
        min = min.length() == 1 ? "0" + min : min;
        sec = sec.length() == 1 ? "0" + sec : sec;
        String formattedTime = hours + " : " + min + " : " + sec;
        chosenDate.setText(formattedDate);
        chosenTime.setText(formattedTime);

        String temp = Integer.toString(simulationInfoService.getOutsideTemp());
        outsideTemp.setText(temp + " °C");
    }
}
