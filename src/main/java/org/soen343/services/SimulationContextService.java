package org.soen343.services;

import org.soen343.models.Model;
import org.soen343.models.house.Individual;
import org.soen343.models.house.Window;
import org.soen343.services.modules.SHCModule;
import org.soen343.services.modules.SHPModule;

import java.util.HashSet;

public class SimulationContextService extends Service {

    private static SimulationContextService simulationContextService = null;

    public static SimulationContextService getInstance() {
        if (simulationContextService == null) {
            simulationContextService = new SimulationContextService();
        }
        return simulationContextService;
    }

    /**
     * Update window state
     *
     * @param windows
     */
    public void updateWindowBlockState(HashSet<Integer> windows) {
        //TODO: Log that we block that window

        for (int id : windows) {
            Window window = Model.getHouse().getWindowById(id);
            window.setBlocked(!window.isBlocked());
        }
        this.notifyObservers(this);
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

            String oldLocation = individual.getLocation();
            individual.setLocation(location);

            if (Model.getSimulationParameters().isAutoModeOn()) {
                SHCModule.getInstance().autoCloseLights(oldLocation);
                SHCModule.getInstance().autoOpenLights(location);
            }

            if (Model.getSimulationParameters().isAwayModeOn()) {
                if (!location.equals("outside"))
                    SHPModule.getInstance().intrusionDetectedDuringAwayMode();
            }

            this.notifyObservers(this);
        }
    }
}
