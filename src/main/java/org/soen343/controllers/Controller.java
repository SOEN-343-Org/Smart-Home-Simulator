package org.soen343.controllers;

import org.soen343.models.Model;

public abstract class Controller {

    public static Model model;

    DashboardController mainController;

    public void setModel(Model model) {
        Controller.model = model;
    }

    protected void setMainController(DashboardController dashboardController) {
        mainController = dashboardController;
    }

}
