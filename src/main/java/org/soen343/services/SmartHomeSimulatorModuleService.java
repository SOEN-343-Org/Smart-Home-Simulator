package org.soen343.services;

import org.soen343.models.Individual;
import org.soen343.models.User;

import java.time.LocalDate;

public class SmartHomeSimulatorModuleService extends Service {

    public SmartHomeSimulatorModuleService() {
    }

    /**
     * Update individual's name
     *
     * @param newName
     * @param idSelected
     * @return success
     */
    public boolean updateIndividualName(String newName, int idSelected) {
        boolean success = model.updateIndividualName(newName, idSelected);
        //TODO: Log that we changed individual's name
        return success;
    }

    /**
     * Add new individual
     *
     * @param name
     * @param role
     * @param location
     * @return success
     */
    public boolean addNewIndividual(String name, String role, String location) {
        String username = User.getUsername();
        boolean success = model.addIndividual(name, role, username, location);
        //TODO: Log that we added a new individual
        return success;
    }

    /**
     * Remove individual
     *
     * @param individualId
     * @return success
     */
    public boolean RemoveIndividual(int individualId) {
        boolean success = model.removeIndividual(individualId);
        //TODO: Log that we removed an individual
        return success;
    }

    /**
     * Update date and time
     *
     * @param date
     */
    public void updateDateTimeDate(LocalDate date) {
        model.dateTime.setDate(date);
    }

    /**
     * Update user individual
     *
     * @param individual
     */
    public void updateUserIndividual(Individual individual) {
        if (individual != null) {
            User.setCurrentIndividual(individual);
            //TODO: Log that we set the user's individual
        }
    }

    /**
     * Update time
     *
     * @param h
     * @param m
     * @param s
     */
    public void updateTime(int h, int m, int s) {
        model.dateTime.setHours(h);
        model.dateTime.setMinutes(m);
        model.dateTime.setSeconds(s);
    }

    /**
     * Update outside temperature
     *
     * @param temp
     */
    public void updateOutsideTemp(Integer temp) { model.outsideTemp.setTemperature(temp); }
}
