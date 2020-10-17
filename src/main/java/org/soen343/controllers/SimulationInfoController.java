package org.soen343.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.soen343.services.SimulationInfoService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SimulationInfoController extends Controller {
    @FXML
    public Label profileName;
    @FXML
    public Label role;
    @FXML
    public Label chosenLocation;
    @FXML
    public Label outsideTemp;
    @FXML
    public Label chosenDate;
    @FXML
    public Label chosenTime;

    SimulationInfoService simulationInfoService;


    public void initializeController() {
        simulationInfoService = new SimulationInfoService();
        setContextBox();
    }

    public void startSimulation(ActionEvent actionEvent) {
        simulationInfoService.setSimulationRunning();
    }

    public void openSimulationContextView(ActionEvent actionEvent) {
        mainController.enterSimulationContext();
    }

    public void setContextBox() {
        profileName.setText(simulationInfoService.getIndividualName());
        role.setText(simulationInfoService.getIndividualRole());
        chosenLocation.setText(simulationInfoService.getIndividualLocation());

        // Format Date and Time
        LocalDate date = simulationInfoService.getContextDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMM dd, yyyy");
        String formattedDate = date.format(formatter);
        String hours = Integer.toString(simulationInfoService.getContextHours());
        String min = Integer.toString(simulationInfoService.getContextMin());
        hours = hours.length() == 1 ? "0" + hours : hours;
        min = min.length() == 1 ? "0" + min : min;
        String formattedTime = hours + " : " + min;
        chosenDate.setText(formattedDate);
        chosenTime.setText(formattedTime + " h");

        String temp = Integer.toString(simulationInfoService.getOutsideTemp());
        outsideTemp.setText(temp + " °C");
    }
}
