package org.soen343.controllers;

import javafx.event.ActionEvent;

public class SimulationInfoController extends Controller {

    public void startSimulation(ActionEvent actionEvent) {
        model.simulationStarted = !model.simulationStarted;
    }

    public void openSimulationContextView(ActionEvent actionEvent) {
        mainController.enterSimulationContext();
    }
}
