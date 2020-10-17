package org.soen343.services;

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
        String username = model.user.getName();
        boolean success = model.addIndividual(name, role, username, location);
        //TODO: Log that we added a new individual
        return success;
    }

    public boolean RemoveIndividual(int individualId) {
        boolean success = model.removeIndividual(individualId);
        //TODO: Log that we removed an individual
        return success;
    }

    public void updateDateTimeHours(int hours) {
        model.dateTime.setHours(hours);
    }

    public void updateDateTimeMinutes(int minutes) {
        model.dateTime.setMinutes(minutes);
    }

    public void updateDateTimeDate(LocalDate date) {
        model.dateTime.setDate(date);
    }

    public void updateOutsideTemp(Integer temp) { model.outsideTemp.setTemperature(temp); }
}
