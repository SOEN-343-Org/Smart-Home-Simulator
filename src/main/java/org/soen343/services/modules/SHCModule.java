package org.soen343.services.modules;

import org.soen343.models.Model;
import org.soen343.models.house.Door;
import org.soen343.models.house.Light;
import org.soen343.models.house.Window;
import org.soen343.services.Service;

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
            boolean state = window.isOpen();
            window.setOpen(!state);
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
}
