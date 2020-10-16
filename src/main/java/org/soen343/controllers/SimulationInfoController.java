package org.soen343.controllers;

import javafx.event.ActionEvent;
import org.soen343.services.SimulationInfoService;

public class SimulationInfoController extends Controller {

    SimulationInfoService simulationInfoService;

    public void initializeController() {
        simulationInfoService = new SimulationInfoService();
    }

    public void startSimulation(ActionEvent actionEvent) {
        simulationInfoService.setSimulationRunning();
    }

    public void openSimulationContextView(ActionEvent actionEvent) {
        mainController.enterSimulationContext();
    }
}
