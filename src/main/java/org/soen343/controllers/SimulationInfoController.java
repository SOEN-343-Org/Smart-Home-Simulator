package org.soen343.controllers;

import javafx.event.ActionEvent;

public class SimulationInfoController {

    DashboardController mainController;

    public void setMainController(DashboardController c) {
        mainController = c;
    }

    public void openIndividualView(ActionEvent actionEvent) {
        mainController.enterIndividualView();
    }
}
