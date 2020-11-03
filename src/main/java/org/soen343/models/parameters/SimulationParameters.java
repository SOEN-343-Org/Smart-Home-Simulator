package org.soen343.models.parameters;

public class SimulationParameters {
    private static DateTime dateTime;
    private static int outsideTemp;
    private static boolean simulationRunning;

    /**
     * Default constructor for SimulationParameters object
     */
    public SimulationParameters() {
        simulationRunning = false;
        dateTime = new DateTime();
        outsideTemp = 20;
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
