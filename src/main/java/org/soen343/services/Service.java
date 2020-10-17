package org.soen343.services;

import org.soen343.models.Individual;
import org.soen343.models.Model;
import org.soen343.models.Room;
import org.soen343.models.User;

import java.util.ArrayList;

public abstract class Service {

    public static Model model = new Model();

    public Service() {

    }

    public boolean SimulationIsRunning() {
        return model.simulationRunning;
    }

    public ArrayList<String> getHouseRoomsName() {
        return model.house.roomsName;
    }

    public String getCurrentUserLocation() {
        return User.getCurrentIndividual().getLocation();
    }

    public ArrayList<Individual> getIndividuals() {
        return model.house.getIndividuals();
    }

    public ArrayList<Room> getHouseRooms() {
        return model.house.getRooms();
    }

    public Individual getCurrentUserIndividual() {
        return User.getCurrentIndividual();
    }

    public void updateIndividualLocation(Individual individual, String location) {
        //TODO: Log that we update location of that individual
        individual.setLocation(location);
    }


}
