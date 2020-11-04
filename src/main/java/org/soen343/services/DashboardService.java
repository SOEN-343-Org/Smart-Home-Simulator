package org.soen343.services;

import javafx.fxml.FXML;
import org.soen343.controllers.SmartHomeSimulatorModuleController;
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
            System.out.println("Cannot start simulation, profile not selected");
            return false;
        }
        Model.getSimulationParameters().setSimulationIsRunning();
        this.notifyObservers(this);
        return true;
        //TODO: Log that we started the simulation
    }

    public void updateTime() {
        if (!Model.getSimulationParameters().isAwayModeOn()) {
            return;
        }
        // Away mode is on here
        Date currentDate = Model.getSimulationParameters().getDateTime().getCalendar().getTime();
        Date from = Model.getSimulationParameters().getAwayModeParameters().getLightsOpenFrom();
        Date to = Model.getSimulationParameters().getAwayModeParameters().getLightsOpenTo();
        if (currentDate.after(from) && currentDate.before(to)) {
            // lights should be on during that time
            SHCModule.getInstance().awayOpenLights();
        } else {
            SHCModule.getInstance().awayCloseLights();
        }
        Date authoritiesDate = Model.getSimulationParameters().getAwayModeParameters().getDateForCallingPoliceAfterBreakIn();

        if (currentDate.equals(authoritiesDate)) {
            // Time to alert the authorities
            SHCModule.getInstance().alertAuthorities();
        }
        // if away mode on: look if lights need to be on or off
        // and look if authorities need to be called
    }
}
