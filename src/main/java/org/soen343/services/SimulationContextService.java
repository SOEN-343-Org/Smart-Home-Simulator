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
        for (int id : windows) {
            Window window = Model.getHouse().getWindowById(id);
            window.setBlocked(!window.isBlocked());
            System.out.println("[Simulation Context] " + (window.isBlocked() ? "Blocked " : "Unblocked ") + " " + window.getName());
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
        if (individual != null) {
            String oldLocation = individual.getLocation();
            individual.setLocation(location);
            System.out.println("[Simulation Context] Updated location of " + individual.getName() + " to " + location);

            SHCModule.getInstance().updateInIndividualLocation(oldLocation, location);

            SHPModule.getInstance().updateInIndividualLocation(individual, location);

        }
    }
}
