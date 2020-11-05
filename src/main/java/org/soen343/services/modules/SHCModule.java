package org.soen343.services.modules;

import org.soen343.models.Model;
import org.soen343.models.User;
import org.soen343.models.house.*;
import org.soen343.services.ConsoleOutputService;
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

        //Check permissions:

        for (int id : windows) {
            Window window = Model.getHouse().getWindowById(id);
            if (window.isBlocked()) {
                ConsoleOutputService.getInstance().warningLog("[SHC Module] Cannot update " + window.getName() + " because it is blocked");
            } else {
                boolean state = window.isOpen();
                window.setOpen(!state);
                ConsoleOutputService.getInstance().infoLog("[SHC Module] " + (!state ? "Opened " : "Closed ") + window.getName());
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

        //Check permissions:

        for (int id : doors) {
            Door door = Model.getHouse().getDoorById(id);
            boolean state = door.isOpen();
            door.setOpen(!state);
            ConsoleOutputService.getInstance().infoLog("[SHC Module] " + (!state ? "Opened " : "Closed ") + door.getName());
        }
        this.notifyObservers(this);
    }

    /**
     * Update light state
     *
     * @param lights
     */
    public void updateLightState(HashSet<Integer> lights) {

        //Check permissions:

        for (int id : lights) {
            Light light = Model.getHouse().getLightById(id);
            boolean state = light.isOpen();
            light.setOpen(!state);
            ConsoleOutputService.getInstance().infoLog("[SHC Module] " + (!state ? "Opened " : "Closed ") + light.getName());
        }
        this.notifyObservers(this);
    }

    public boolean setAutoMode() {
        //Check permissions:
        Individual ind = User.getCurrentIndividual();
        if (ind.getRole().equals("Family Adult") || ind.getRole().equals("Family Child")) {
            Model.getSimulationParameters().setAutoMode();
            ConsoleOutputService.getInstance().infoLog("[SHC Module] [Auto Mode] " + ind.getName() + " has set Auto mode to " + (Model.getSimulationParameters().isAutoModeOn() ? "ON" : "OFF"));
            return true;
        }
        // User does not have the permission
        ConsoleOutputService.getInstance().warningLog("[SHC Module] [Auto Mode] " + ind.getName() + " does not have the permission to set auto mode");
        return false;
    }

    public void resetAutoMode() {
        Model.getSimulationParameters().setAutoMode();
        // Simulation is off while auto mode was on, so we turn auto mode off
        ConsoleOutputService.getInstance().warningLog("[SHC Module] [Auto Mode] Auto Mode shut down because Simulation stopped");
        notifyObservers(this);
    }

    public void autoOpenLights(String location) {
        if (location.equals("outside")) return;
        Room room = Model.getHouse().getRoomByName(location);
        for (Light light : room.getLights()) {
            if (!light.isOpen()) {
                light.setOpen(true);
                ConsoleOutputService.getInstance().infoLog("[SHC Module] [Auto Mode] Opened " + light.getName());
            }
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
            if (light.isOpen()) {
                light.setOpen(false);
                ConsoleOutputService.getInstance().infoLog("[SHC Module] [Auto Mode] Closed " + light.getName());
            }
        }
    }


    public void awayOpenLights(ArrayList<Light> lights) {
        boolean needUpdate = false;
        for (Light l : lights) {
            if (!l.isOpen()) {
                l.setOpen(true);
                ConsoleOutputService.getInstance().infoLog("[SHC Module] [Away Mode] Opened " + l.getName());
                needUpdate = true;
            }
        }
        if (needUpdate)
            notifyObservers(this);
    }

    public void alertAuthorities() {
        ConsoleOutputService.getInstance().criticalLog("[SHC Module] [Away Mode] Authorities have been alerted");
    }

    public void intrusionDetectedDuringAwayMode(Individual individual) {
        ConsoleOutputService.getInstance().criticalLog("[SHC Module] [Away Mode] Intruder " + individual.getName() + " detected");
    }

    public void awayCloseLights(ArrayList<Light> lights) {
        boolean needUpdate = false;
        for (Light l : lights) {
            if (l.isOpen()) {
                l.setOpen(false);
                ConsoleOutputService.getInstance().infoLog("[SHC Module] [Away Mode] Closed " + l.getName());
                needUpdate = true;
            }
        }
        if (needUpdate)
            notifyObservers(this);
    }

    public void awayCloseWindows(ArrayList<Window> allWindows) {
        boolean needUpdate = false;
        for (Window w : allWindows) {
            if (w.isOpen()) {
                if (!w.isBlocked()) {
                    w.setOpen(false);
                    ConsoleOutputService.getInstance().infoLog("[SHC Module] [Away Mode] Closed and Locked " + w.getName());
                    needUpdate = true;
                } else {
                    ConsoleOutputService.getInstance().warningLog("[SHC Module] [Away Mode] Could not close " + w.getName() + " because it is blocked");
                }
            }
        }
        if (needUpdate)
            notifyObservers(this);
    }

    public void awayCloseDoors(ArrayList<Door> allDoors) {
        boolean needUpdate = false;
        for (Door d : allDoors) {
            if (d.isOpen()) {
                d.setOpen(false);
                ConsoleOutputService.getInstance().infoLog("[SHC Module] [Away Mode] Closed and Locked " + d.getName());
                needUpdate = true;
            }
        }
        if (needUpdate)
            notifyObservers(this);
    }

    public void updateInIndividualLocation(String oldLocation, String location) {
        if (Model.getSimulationParameters().isAutoModeOn()) {
            SHCModule.getInstance().autoCloseLights(oldLocation);
            SHCModule.getInstance().autoOpenLights(location);
        }
        notifyObservers(this);
    }
}
