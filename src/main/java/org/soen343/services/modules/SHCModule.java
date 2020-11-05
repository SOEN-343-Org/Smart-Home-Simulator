package org.soen343.services.modules;

import org.soen343.models.Model;
import org.soen343.models.User;
import org.soen343.models.house.*;
import org.soen343.models.permissions.*;
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
     * @param windows Set of selected Window Id's
     */
    public void updateWindowState(HashSet<Integer> windows) {
        HashSet<Integer> validWindowIds = new HashSet<>();

        //Check permissions:
        SHCUserWindowRule r = new SHCUserWindowRule();

        for (int i : windows) {
            if (r.validate(i)) {
                validWindowIds.add(i);
            } else {
                ConsoleOutputService.getInstance().warningLog("[SHC Module] User does not have the permission to update " + Model.getHouse().getWindowById(i).getName());
            }
        }

        for (int id : validWindowIds) {
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
        HashSet<Integer> validDoorIds = new HashSet<>();

        //Check permissions:
        SHCUserDoorRule r = new SHCUserDoorRule();

        for (int i : doors) {
            if (r.validate(i)) {
                validDoorIds.add(i);
            } else {
                ConsoleOutputService.getInstance().warningLog("[SHC Module] User does not have the permission to update " + Model.getHouse().getDoorById(i).getName());
            }
        }

        for (int id : validDoorIds) {
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
        HashSet<Integer> validLightIds = new HashSet<>();

        //Check permissions:
        SHCUserLightRule r = new SHCUserLightRule();

        for (int i : lights) {
            if (r.validate(i)) {
                validLightIds.add(i);
            } else {
                ConsoleOutputService.getInstance().warningLog("[SHC Module] User does not have the permission to update " + Model.getHouse().getLightById(i).getName());
            }
        }

        for (int id : validLightIds) {
            Light light = Model.getHouse().getLightById(id);
            boolean state = light.isOpen();
            light.setOpen(!state);
            ConsoleOutputService.getInstance().infoLog("[SHC Module] " + (!state ? "Opened " : "Closed ") + light.getName());
        }
        this.notifyObservers(this);
    }

    /**
     * Sets the auto mode
     * @return true is the auto mode has been set
     */
    public boolean setAutoMode() {
        Individual ind = User.getCurrentIndividual();

        //Check permissions:
        if (validate()) {
            Model.getSimulationParameters().setAutoMode();
            ConsoleOutputService.getInstance().infoLog("[SHC Module] [Auto Mode] " + ind.getName() + " has set Auto mode to " + (Model.getSimulationParameters().isAutoModeOn() ? "ON" : "OFF"));
            return true;
        }
        // User does not have the permission
        ConsoleOutputService.getInstance().warningLog("[SHC Module] [Auto Mode] " + ind.getName() + " does not have the permission to set auto mode");
        return false;
    }

    /**
     * Check auto mode permission
     * @return true if individual can set auto mode
     */
    private boolean validate() {
        Rule r = new SHCRule();
        Rule autoModeRule = r.createRule("AutoMode", 0);
        boolean isValid = autoModeRule.validate(0);
        if (isValid) return true;
        return false;
    }

    /**
     * Turns the auto mode off if simulation is turned off while auto mode is on
     */
    public void resetAutoMode() {
        Model.getSimulationParameters().setAutoMode();
        // Simulation is off while auto mode was on, so we turn auto mode off
        ConsoleOutputService.getInstance().warningLog("[SHC Module] [Auto Mode] Auto Mode shut down because Simulation stopped");
        notifyObservers(this);
    }

    /**
     * Opens the light at location during auto
     * @param location Individual`s location
     */
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

    /**
     * Closes the light at location during auto
     * @param location Individual's location
     */
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


    /**
     * Opens the light during away mode
     * @param lights list of lights to open
     */
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

    /**
     * Log that we alert the authorities
     */
    public void alertAuthorities() {
        ConsoleOutputService.getInstance().criticalLog("[SHC Module] [Away Mode] Authorities have been alerted");
    }

    /**
     * Log that an intruder has been detected
     * @param individual Individual that broke into the house
     */
    public void intrusionDetectedDuringAwayMode(Individual individual) {
        ConsoleOutputService.getInstance().criticalLog("[SHC Module] [Away Mode] Intruder " + individual.getName() + " detected");
    }

    /**
     * CLose lights during away mode
     * @param lights List of Lights
     */
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

    /**
     * Closes windows during away mode
     * @param allWindows List of windows
     */
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

    /**
     * Closes doors during away mode
     * @param allDoors List of Doors
     */
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

    /**
     * Called when an individual's location has been updated, to open/close lights during auto mode
     * @param oldLocation old location
     * @param location new location
     */
    public void updateInIndividualLocation(String oldLocation, String location) {
        if (Model.getSimulationParameters().isAutoModeOn()) {
            SHCModule.getInstance().autoCloseLights(oldLocation);
            SHCModule.getInstance().autoOpenLights(location);
        }
        notifyObservers(this);
    }
}
