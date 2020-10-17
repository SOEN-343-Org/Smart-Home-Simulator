package org.soen343.services;

import org.soen343.models.Window;

public class SimulationContextService extends Service {

    public SimulationContextService() {
    }

    public void updateWindowBlockState(int windowId) {
        //TODO: Log that we block that window
        Window window = model.house.getWindowById(windowId);
        window.setBlocked(!window.isBlocked());
    }
}
