package org.soen343.services.modules;

import org.soen343.models.Model;
import org.soen343.models.User;
import org.soen343.models.house.*;
import org.soen343.models.permissions.Rule;
import org.soen343.models.permissions.SHCRule;
import org.soen343.models.permissions.Validator;
import org.soen343.services.Service;

import java.util.ArrayList;
import java.util.HashSet;

public class SHCModule extends Service implements Validator {

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
        Window w = new Window(0);

        for (int i : windows) {
            if (w.validate(i)) {
                validWindowIds.add(i);
            }
        }

        for (int id : validWindowIds) {
            Window window = Model.getHouse().getWindowById(id);
            if (window.isBlocked()) {
                System.out.println("[SHC Module] " + window.getName() + " is blocked");
            } else {
                boolean state = window.isOpen();
                window.setOpen(!state);
                System.out.println("[SHC Module] " + (!state ? "Opened " : "Closed ") + window.getName());
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
        Door d = new Door(0);

        for (int i : doors) {
            if (d.validate(i)) {
                validDoorIds.add(i);
            }
        }

        for (int id : validDoorIds) {
            Door door = Model.getHouse().getDoorById(id);
            boolean state = door.isOpen();
            door.setOpen(!state);
            System.out.println("[SHC Module] " + (!state ? "Opened " : "Closed ") + door.getName());
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
        Light l = new Light(0);

        for (int i : lights) {
            if (l.validate(i)) {
                validLightIds.add(i);
            }
        }

        for (int id : validLightIds) {
            Light light = Model.getHouse().getLightById(id);
            boolean state = light.isOpen();
            light.setOpen(!state);
            System.out.println("[SHC Module] " + (!state ? "Opened " : "Closed ") + light.getName());
        }
        this.notifyObservers(this);
    }

    public boolean setAutoMode() {
        Individual ind = User.getCurrentIndividual();

        //Check permissions:
        if (validate(0)) {
            if (ind.getRole().equals("Family Adult") || ind.getRole().equals("Family Child")) {
                Model.getSimulationParameters().setAutoMode();
                System.out.println("[SHC Module] [Auto Mode] " + ind.getName() + " has set Auto mode to " + (Model.getSimulationParameters().isAutoModeOn() ? "ON" : "OFF"));
                return true;
            }
        }
        // User does not have the permission
        System.out.println("[SHC Module] [Auto Mode] " + ind.getName() + " does not have the permission to set auto mode");
        return false;
    }

    @Override
    public boolean validate(int id) {
        Rule r = new SHCRule();
        Rule autoModeRule = r.createRule("AutoMode", id);
        boolean isValid = autoModeRule.validate(id);
        if (isValid) return true;
        return false;
    }

    public void autoOpenLights(String location) {
        if (location.equals("outside")) return;
        Room room = Model.getHouse().getRoomByName(location);
        for (Light light : room.getLights()) {
            if (!light.isOpen()) {
                light.setOpen(true);
                System.out.println("[SHC Module] [Auto Mode] Opened " + light.getName());
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
                System.out.println("[SHC Module] [Auto Mode] Closed " + light.getName());
            }
        }
    }


    public void awayOpenLights(ArrayList<Light> lights) {
        boolean needUpdate = false;
        for (Light l : lights) {
            if (!l.isOpen()) {
                l.setOpen(true);
                System.out.println("[SHC Module] [Away Mode] Opened " + l.getName());
                needUpdate = true;
            }
        }
        if (needUpdate)
            notifyObservers(this);
    }

    public void alertAuthorities() {
        System.out.println("[SHC Module] [Away Mode] Authorities have been alerted");
    }

    public void intrusionDetectedDuringAwayMode(Individual individual) {
        System.out.println("[SHC Module] [Away Mode] Intruder " + individual.getName() + " detected");
    }

    public void awayCloseLights(ArrayList<Light> lights) {
        boolean needUpdate = false;
        for (Light l : lights) {
            if (l.isOpen()) {
                l.setOpen(false);
                System.out.println("[SHC Module] [Away Mode] Closed " + l.getName());
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
                    System.out.println("[SHC Module] [Away Mode] Closed and Locked " + w.getName());
                    needUpdate = true;
                } else {
                    System.out.println("[SHC Module] [Away Mode] Could not close " + w.getName() + " because it is blocked");
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
                System.out.println("[SHC Module] [Away Mode] Closed and Locked " + d.getName());
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
