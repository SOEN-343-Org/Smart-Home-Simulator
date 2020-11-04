package org.soen343.services.modules;

import org.soen343.models.Model;
import org.soen343.models.User;
import org.soen343.models.house.*;
import org.soen343.services.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
                System.out.println(window + " is blocked");
            } else {
                boolean state = window.isOpen();
                window.setOpen(!state);
                System.out.println((!state ? "Opened" : "Closed") + window);
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
            System.out.println((!state ? "Opened" : "Closed") + door);
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
            // Simulation is not on//TODO: TO REMOVE WHEN PANEL WILL BE DISABLED
            //TODO: LOG IT
            return;
        }
        for (int id : lights) {
            //TODO: Log that we open/close that light

            Light light = Model.getHouse().getLightById(id);
            boolean state = light.isOpen();
            light.setOpen(!state);
            System.out.println((!state ? "Opened" : "Closed") + light);
        }
        this.notifyObservers(this);
    }

    public boolean setAutoMode() {
        if (!Model.getSimulationParameters().isSimulationRunning()) {
            // Simulation is not on
            //TODO: TO REMOVE WHEN PANEL WILL BE DISABLED
            return false;
        }
        if (User.getCurrentIndividual() != null) { //TODO: ALL THESE CHECKS WILL ALSO GO BECAUSE THE SIMULATION WILL BE ON THEREFOERE A PROFILE HAS BEEN SELECTED
            Model.getSimulationParameters().setAutoMode();
            //TODO: LOG IT
            return true;
        }
        System.out.println("Auto mode set to " + Model.getSimulationParameters().isAutoModeOn());
        //TODO: LOG IT
        return false;
    }

    public void autoOpenLights(String location) {
        if (location.equals("outside")) return;
        Room room = Model.getHouse().getRoomByName(location);
        for (Light light : room.getLights()) {
            if (!light.isOpen()) {
                //TODO: Log that we open that light
                light.setOpen(true);
                System.out.println("Opened " + light + " because of auto mode");
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
                //TODO: Log that we close that light
                light.setOpen(false);
                System.out.println("Closed " + light + " because of auto mode");
            }
        }
    }

    public void closeAllLights() {
        for (Light l : Model.getHouse().getAllLights()) {
            if (l.isOpen()) {
                //TODO: Log we close light
                l.setOpen(false);
                System.out.println("Closed " + l);
            }
        }
    }

    public void awayOpenLights() {
        boolean needUpdate = false;
        for (Light l : Model.getSimulationParameters().getAwayModeParameters().getOpenLights()) {
            if (!l.isOpen()) {
                //TODO: Log we open light
                System.out.println("Opened " + l + " because of away mode");
                l.setOpen(true);
                needUpdate = true;
            }
        }
        if (needUpdate)
            notifyObservers(this);
    }

    public void awayCloseLights() {
        boolean needUpdate = false;
        for (Light l : Model.getSimulationParameters().getAwayModeParameters().getOpenLights()) {
            if (l.isOpen()) {
                //TODO: Log we close light
                System.out.println("Closed " + l + " because of awaymode");
                l.setOpen(false);
                needUpdate = true;
            }
        }
        if (needUpdate)
            notifyObservers(this);
    }

    public void alertAuthorities() {
        //TODO: Log that we have alerted the authorities.
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        System.out.println("[SHC Module] [" + dateFormat.format(Model.getSimulationParameters().getDateTime().getCalendar().getTime()) + " " + timeFormat.format(Model.getSimulationParameters().getDateTime().getCalendar().getTime()) + "] Authorities have been alerted");
    }

    public void intrusionDetectedDuringAwayMode() {
        //TODO: Log that we have alerted the authorities.
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        System.out.println("[SHC Module] [" + dateFormat.format(Model.getSimulationParameters().getDateTime().getCalendar().getTime()) + " " + timeFormat.format(Model.getSimulationParameters().getDateTime().getCalendar().getTime()) + "] Intrusion detected");
    }

    public void closeAllWindows() {
        for (Window w : Model.getHouse().getAllWindows()) {
            if (w.isOpen()) {
                if (!w.isBlocked()) {
                    //TODO: Log we close window
                    w.setOpen(false);
                    System.out.println("Closed " + w);
                } else {
                    System.out.println("Could not close " + w + " because it is blocked");
                }
            }
        }
    }

    public void closeAllDoors() {
        for (Door d : Model.getHouse().getAllDoors()) {
            //TODO: Log we close door
            if (d.isOpen()) {
                d.setOpen(false);
                System.out.println("Closed " + d);
            }

        }
    }
}
