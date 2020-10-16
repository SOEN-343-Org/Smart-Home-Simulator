package org.soen343.services;

import org.soen343.models.Door;
import org.soen343.models.Light;
import org.soen343.models.Window;

public class SmartHomeCoreModuleService extends Service {

    public SmartHomeCoreModuleService() {
    }

    public void updateWindowState(int windowId) {
        //TODO: Log that we open/close that window
        Window window = model.house.getWindowById(windowId);
        boolean state = window.isOpen();
        window.setOpen(!state);
    }

    public void updateDoorState(int doorId) {
        //TODO: Log that we open/close that door
        Door door = model.house.getDoorById(doorId);
        boolean state = door.isOpen();
        door.setOpen(!state);
    }

    public void updateLightState(int lightId) {
        //TODO: Log that we open/close that light
        Light light = model.house.getLightById(lightId);
        boolean state = light.isOpen();
        light.setOpen(!state);
    }
}
