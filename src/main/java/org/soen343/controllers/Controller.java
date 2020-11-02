package org.soen343.controllers;

import org.soen343.services.Observable;

public abstract class Controller implements Observer{

    DashboardController mainController;

    /**
     *
     * @param dashboardController
     */
    protected void setMainController(DashboardController dashboardController) {
        mainController = dashboardController;
    }

    abstract void initializeController();

    abstract void update();

    @Override
    public void update(Observable observable) {
        this.update();
    }
}
