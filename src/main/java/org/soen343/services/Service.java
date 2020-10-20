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

    /**
     * Check if simulation is running
     *
     * @return simulationRunning
     */
    public boolean SimulationIsRunning() {
        return model.simulationRunning;
    }

    /**
     * Get room name
     *
     * @return roomsName
     */
    public ArrayList<String> getHouseRoomsName() {
        return model.house.roomsName;
    }

    /**
     * Get current user location
     *
     * @return location
     */
    public String getCurrentUserLocation() {
        if (User.getCurrentIndividual() != null) {
            return User.getCurrentIndividual().getLocation();
        }
        return "outside";
    }

    /**
     * Get array of individuals
     *
     * @return individuals
     */
    public ArrayList<Individual> getIndividuals() {
        return model.house.getIndividuals();
    }

    /**
     * Get house rooms
     *
     * @return rooms
     */
    public ArrayList<Room> getHouseRooms() {
        return model.house.getRooms();
    }

    /**
     * Get current user individual
     *
     * @return current individual
     */
    public Individual getCurrentUserIndividual() {
        return User.getCurrentIndividual();
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
        }
    }


}
