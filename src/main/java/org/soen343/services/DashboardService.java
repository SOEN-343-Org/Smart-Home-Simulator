package org.soen343.services;

import javafx.fxml.FXML;
import org.soen343.controllers.SmartHomeSimulatorModuleController;
import org.soen343.models.Model;
import org.soen343.models.User;
import org.soen343.models.house.Individual;

public class DashboardService extends Service {

    private static DashboardService dashboardService = null;

    public static DashboardService getInstance() {
        if (dashboardService == null) {
            dashboardService = new DashboardService();
        }
        return dashboardService;
    }

    /**
     * Populate individuals
     */
    public void populateIndividuals() {
        Model.setIndividualsFromUser(User.getUsername());
    }


    /**
     * Set simulation running
     *
     * @return success
     */
    public boolean setSimulationRunning() {
        Individual ind = User.getCurrentIndividual();
        if (ind == null) {
            System.out.println("Cannot start simulation, profile not selected");
            return false;
        }
        Model.getSimulationParameters().setSimulationIsRunning();
        this.notifyObservers(this);
        return true;
        //TODO: Log that we started the simulation
    }
}
