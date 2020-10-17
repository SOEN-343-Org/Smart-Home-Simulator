package org.soen343.services;

import org.soen343.models.Individual;
import org.soen343.models.User;

import java.time.LocalDate;

public class SimulationInfoService extends Service {

    public SimulationInfoService() {
    }

    public boolean setSimulationRunning() {

        boolean success  = model.setSimulationIsRunning();
        return success;
        //TODO: Log that we started the simulation
    }

    public String getIndividualName() {
        if (User.getCurrentIndividual() == null) {
            return "Profile not set";
        }
        return User.getCurrentIndividual().getName();
    }

    public String getIndividualRole() {
        if (User.getCurrentIndividual() == null) {
            return "Profile not set";
        }
        return User.getCurrentIndividual().getRole();
    }

    public String getIndividualLocation() {
        if (User.getCurrentIndividual() == null) {
            return "Profile not set";
        }
        return User.getCurrentIndividual().getLocation();
    }

    public LocalDate getContextDate() {
        return model.dateTime.getDate();
    }

    public Integer getContextHours() {
        return model.dateTime.getHours();
    }

    public Integer getContextMin() {
        return model.dateTime.getMinutes();
    }

    public Integer getContextSec() {
        return model.dateTime.getSeconds();
    }

    public Integer getOutsideTemp() {
        return model.outsideTemp.getTemperature();
    }


    public boolean getSimulationStatus() {
        return model.simulationRunning;
    }
}
