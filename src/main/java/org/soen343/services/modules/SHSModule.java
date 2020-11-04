package org.soen343.services.modules;

import org.soen343.models.Model;
import org.soen343.models.User;
import org.soen343.models.house.Individual;
import org.soen343.services.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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
        if (individual != null) {
            individual.setLocation(location);
            System.out.println("[SHS Module] Updated " + individual.getName() + " location to " + location);
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
        System.out.println("[SHS Module] Updated individual " + idSelected + "'s name to " + newName);
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
        System.out.println("[SHS Module] Added new individual " + name + " at location " + location + " with role " + role);
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
        System.out.println("[SHS Module] Removed individual " + individualId);
        notifyObservers(this);
        return success;
    }

    /**
     * Update user individual
     *
     * @param individual
     */
    public void updateUserIndividual(Individual individual) {
        if (individual != null) {
            User.setCurrentIndividual(individual);
            System.out.println("[SHS Module] Logged into individual #" + individual.getId() + " " + individual.getName());
            notifyObservers(this);
        }
    }

    /**
     * Update date and time
     *
     * @param date
     */
    public void updateDateTimeDate(LocalDate date) {
        Model.getSimulationParameters().getDateTime().setCalendarDate(date);
        System.out.println("[SHS Module] Updated Simulation's Date to " + DateTimeFormatter.ofPattern("MM/dd/yy").format(date));
        notifyObservers(this);
    }

    /**
     * Update time
     */
    public void updateTime(Date date) {
        Model.getSimulationParameters().getDateTime().setCalendarTime(date);
        System.out.println("[SHS Module] Updated Simulation's Time to " + new SimpleDateFormat("HH:mm:ss").format(date));
        notifyObservers(this);
    }

    /**
     * Update outside temperature
     *
     * @param temp
     */
    public void updateOutsideTemp(Integer temp) {
        Model.getSimulationParameters().setOutsideTemp(temp);
        System.out.println("[SHS Module] Updated outside temperature to " + temp + "°C");
        notifyObservers(this);
    }
}
