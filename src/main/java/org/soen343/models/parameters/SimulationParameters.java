package org.soen343.models.parameters;

public class SimulationParameters {
    private static DateTime dateTime;
    private static int outsideTemp;
    private static boolean simulationRunning;
    private static boolean autoMode;

    /**
     * Default constructor for SimulationParameters object
     */
    public SimulationParameters() {
        simulationRunning = false;
        dateTime = new DateTime();
        outsideTemp = 20;
        autoMode = false;
    }

    public boolean isAutoModeOn() {
        return autoMode;
    }

    public void setAutoMode() {
        autoMode = !autoMode;
    }

    public boolean isSimulationRunning() {
        return simulationRunning;
    }

    public int getOutsideTemp() {
        return outsideTemp;
    }

    public void setOutsideTemp(int temp) {
        outsideTemp = temp;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setSimulationIsRunning() {
        simulationRunning = !simulationRunning;
    }

}
