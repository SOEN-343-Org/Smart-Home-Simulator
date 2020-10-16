package org.soen343.controllers;

public abstract class Controller {

    DashboardController mainController;

    protected void setMainController(DashboardController dashboardController) {
        mainController = dashboardController;
    }

    abstract void initializeController();
}
