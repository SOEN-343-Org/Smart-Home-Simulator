package org.soen343.services.modules;

import org.soen343.models.Model;
import org.soen343.models.User;
import org.soen343.models.house.*;
import org.soen343.services.Service;

import java.util.ArrayList;
import java.util.HashSet;

public class SHCModule extends Service {

    private static SHCModule shcModule = null;

    public static SHCModule getInstance() {
        if (shcModule == null) {
            shcModule = new SHCModule();
        }
        return shcModule;
    }

    /**
     * Update window state
     *
     * @param windows
     */
    public void updateWindowState(HashSet<Integer> windows) {
        if (!Model.getSimulationParameters().isSimulationRunning()) {
            // Simulation is not on
            //TODO: LOG IT
            return;
        }
        for (int id : windows) {

            //TODO: Log that we open/close that window
            Window window = Model.getHouse().getWindowById(id);
            if (window.isBlocked()) {
                //TODO: LOG that window is blocked
            } else {
                boolean state = window.isOpen();
                window.setOpen(!state);
            }
        }
        this.notifyObservers(this);
    }

    /**
     * Update door state
     *
     * @param doors
     */
    public void updateDoorState(HashSet<Integer> doors) {
        if (!Model.getSimulationParameters().isSimulationRunning()) {
            // Simulation is not on
            //TODO: LOG IT
            return;
        }
        for (int id : doors) {
            //TODO: Log that we open/close that door
            Door door = Model.getHouse().getDoorById(id);
            boolean state = door.isOpen();
            door.setOpen(!state);
        }
        this.notifyObservers(this);
    }

    /**
     * Update light state
     *
     * @param lights
     */
    public void updateLightState(HashSet<Integer> lights) {
        if (!Model.getSimulationParameters().isSimulationRunning()) {
            // Simulation is not on
            //TODO: LOG IT
            return;
        }
        for (int id : lights) {
            //TODO: Log that we open/close that light
            Light light = Model.getHouse().getLightById(id);
            boolean state = light.isOpen();
            light.setOpen(!state);
        }
        this.notifyObservers(this);
    }

    public boolean setAutoMode() {
        if (!Model.getSimulationParameters().isSimulationRunning()) {
            // Simulation is not on
            //TODO: LOG IT
            return false;
        }
        if (User.getCurrentIndividual() != null) {
            Model.getSimulationParameters().setAutoMode();
            //TODO: LOG IT
            return true;
        }
        //TODO: LOG IT
        return false;
    }

    public void autoOpenLights(String location) {
        if (location.equals("outside")) return;
        Room room = Model.getHouse().getRoomByName(location);
        for (Light light : room.getLights()) {
            //TODO: Log that we open that light
            light.setOpen(true);
        }
    }

    public void autoCloseLights(String location) {
        if (location.equals("outside")) return;

        ArrayList<Individual> individuals = Model.getHouse().getIndividuals();
        for (Individual ind : individuals) {
            if (ind.getLocation().equals(location)) {
                // An individual is still in the room so we dont close the lights
                return;
            }
        }

        Room room = Model.getHouse().getRoomByName(location);

        for (Light light : room.getLights()) {
            //TODO: Log that we close that light
            light.setOpen(false);
        }
    }
}
