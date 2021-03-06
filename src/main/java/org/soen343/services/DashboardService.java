package org.soen343.services;

import org.soen343.models.Model;
import org.soen343.models.User;
import org.soen343.models.house.Individual;
import org.soen343.services.modules.SHHModule;

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
            ConsoleOutputService.getInstance().warningLog("Cannot start simulation, profile not selected");
            return false;
        }
        Model.getSimulationParameters().setSimulationIsRunning();

        if (Model.getSimulationParameters().isSimulationRunning()) {
            ConsoleOutputService.getInstance().infoLog("Simulation has started");
            SHHModule.getInstance().resetHVAC();
        } else {
            ConsoleOutputService.getInstance().infoLog("Simulation has stopped");
            SHHModule.getInstance().resetHVAC();
        }

        this.notifyObservers(this);
        return true;
    }
}
