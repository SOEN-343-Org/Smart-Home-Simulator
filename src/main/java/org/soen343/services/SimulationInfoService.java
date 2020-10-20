package org.soen343.services;

import org.soen343.models.Individual;
import org.soen343.models.User;

import java.time.LocalDate;

public class SimulationInfoService extends Service {

    public SimulationInfoService() {
    }

    /**
     * Set simulation running
     *
     * @return success
     */
    public boolean setSimulationRunning() {

        boolean success  = model.setSimulationIsRunning();
        return success;
        //TODO: Log that we started the simulation
    }

    /**
     * Get individual name
     *
     * @return name or not set
     */
    public String getIndividualName() {
        if (User.getCurrentIndividual() == null) {
            return "Profile not set";
        }
        return User.getCurrentIndividual().getName();
    }

    /**
     * Get individual role
     *
     * @return role or not set
     */
    public String getIndividualRole() {
        if (User.getCurrentIndividual() == null) {
            return "Profile not set";
        }
        return User.getCurrentIndividual().getRole();
    }

    /**
     * Get individual location
     *
     * @return location or not set
     */
    public String getIndividualLocation() {
        if (User.getCurrentIndividual() == null) {
            return "Profile not set";
        }
        return User.getCurrentIndividual().getLocation();
    }

    /**
     * Get context date
     *
      * @return date
     */
    public LocalDate getContextDate() {
        return model.dateTime.getDate();
    }

    /**
     * Get context hours
     *
     * @return hours
     */
    public Integer getContextHours() {
        return model.dateTime.getHours();
    }

    /**
     * Get context minute
     *
     * @return minute
     */
    public Integer getContextMin() {
        return model.dateTime.getMinutes();
    }

    /**
     * Get context second
     *
     * @return seconds
     */
    public Integer getContextSec() {
        return model.dateTime.getSeconds();
    }

    /**
     * Get outside temperature
     *
     * @return outside temperature
     */
    public Integer getOutsideTemp() {
        return model.outsideTemp.getTemperature();
    }

    /**
     * Get simulation status
     *
     * @return status
     */
    public boolean getSimulationStatus() {
        return model.simulationRunning;
    }
}
