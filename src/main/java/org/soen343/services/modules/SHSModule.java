package org.soen343.services.modules;

import org.soen343.models.Model;
import org.soen343.models.User;
import org.soen343.models.house.Individual;
import org.soen343.services.Service;

import java.time.LocalDate;

public class SHSModule extends Service {

    private static SHSModule shsModule = null;

    public static SHSModule getInstance() {
        if (shsModule == null) {
            shsModule = new SHSModule();
        }
        return shsModule;
    }

    /**
     * Update individual location
     *
     * @param individual
     * @param location
     */
    public void updateIndividualLocation(Individual individual, String location) {
        //TODO: Log that we update location of that individual
        if (individual != null) {
            individual.setLocation(location);
            notifyObservers(this);
        }
    }

    /**
     * Update individual's name
     *
     * @param newName
     * @param idSelected
     * @return success
     */
    public boolean updateIndividualName(String newName, int idSelected) {
        boolean success = Model.updateIndividualName(newName, idSelected);
        //TODO: Log that we changed individual's name
        notifyObservers(this);
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
        boolean success = Model.addIndividual(name, role, username, location);
        //TODO: Log that we added a new individual
        notifyObservers(this);
        return success;
    }

    /**
     * Remove individual
     *
     * @param individualId
     * @return success
     */
    public boolean removeIndividual(int individualId) {
        boolean success = Model.removeIndividual(individualId);
        //TODO: Log that we removed an individual
        notifyObservers(this);
        return success;
    }

    /**
     * Update date and time
     *
     * @param date
     */
    public void updateDateTimeDate(LocalDate date) {
        Model.getSimulationParameters().getDateTime().setDate(date);
        notifyObservers(this);
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
            notifyObservers(this);
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
        Model.getSimulationParameters().getDateTime().setHours(h);
        Model.getSimulationParameters().getDateTime().setMinutes(m);
        Model.getSimulationParameters().getDateTime().setSeconds(s);
        notifyObservers(this);
    }

    /**
     * Update outside temperature
     *
     * @param temp
     */
    public void updateOutsideTemp(Integer temp) {
        Model.getSimulationParameters().setOutsideTemp(temp);
        notifyObservers(this);
    }
}
