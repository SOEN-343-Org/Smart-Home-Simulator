package org.soen343.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SimulationInfoService extends Service {

    public SimulationInfoService() {
    }

    public void setSimulationRunning() {
        model.simulationRunning = !model.simulationRunning;

    }

    public String getIndividualName() { return model.user.getCurrentIndividual().getName(); }
    public String getIndividualRole() { return model.user.getCurrentIndividual().getRole(); }
    public String getIndividualLocation() { return model.user.getCurrentIndividual().getLocation(); }

    public LocalDate getContextDate() { return model.dateTime.getDate(); }
    public Integer getContextHours() { return model.dateTime.getHours(); }
    public Integer getContextMin() { return model.dateTime.getMinutes(); }


}
