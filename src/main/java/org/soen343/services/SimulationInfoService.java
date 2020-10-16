package org.soen343.services;

public class SimulationInfoService extends Service {

    public SimulationInfoService() {
    }

    public void setSimulationRunning() {
        model.simulationRunning = !model.simulationRunning;

    }

}
