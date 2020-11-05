package org.soen343.services;

import org.soen343.models.Model;
import org.soen343.models.User;
import org.soen343.models.house.Individual;
import org.soen343.services.modules.SHCModule;

import java.util.Date;

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
            ConsoleOutputService.getInstance().errorLog("Cannot start simulation, profile not selected");
            return false;
        }
        Model.getSimulationParameters().setSimulationIsRunning();

        if(Model.getSimulationParameters().isSimulationRunning() == true) {
            ConsoleOutputService.getInstance().infoLog("Simulation has started");
        }
        else {
            ConsoleOutputService.getInstance().infoLog("Simulation has stopped");
        }

        this.notifyObservers(this);
        return true;
    }
}
