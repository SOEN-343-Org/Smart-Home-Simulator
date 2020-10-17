package org.soen343.services;

import org.soen343.models.Individual;
import org.soen343.models.User;

import java.time.LocalDate;

public class SmartHomeSimulatorModuleService extends Service {

    public SmartHomeSimulatorModuleService() {
    }


    public boolean updateIndividualName(String newName, int idSelected) {
        boolean success = model.updateIndividualName(newName, idSelected);
        //TODO: Log that we changed individual's name
        return success;
    }

    public boolean addNewIndividual(String name, String role, String location) {
        String username = User.getUsername();
        boolean success = model.addIndividual(name, role, username, location);
        //TODO: Log that we added a new individual
        return success;
    }

    public boolean RemoveIndividual(int individualId) {
        boolean success = model.removeIndividual(individualId);
        //TODO: Log that we removed an individual
        return success;
    }

    public void updateDateTimeDate(LocalDate date) {
        model.dateTime.setDate(date);
    }

    public void updateUserIndividual(Individual individual) {
        if (individual != null) {
            User.setCurrentIndividual(individual);
            //TODO: Log that we set the user's individual
        }
    }

    public void updateTime(int h, int m, int s) {
        model.dateTime.setHours(h);
        model.dateTime.setMinutes(m);
        model.dateTime.setSeconds(s);
    }

    public void updateOutsideTemp(Integer temp) { model.outsideTemp.setTemperature(temp); }
}
