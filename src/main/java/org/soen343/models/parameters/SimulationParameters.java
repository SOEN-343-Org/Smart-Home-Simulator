package org.soen343.models.parameters;

public class SimulationParameters {
    private static DateTime dateTime;
    private static int outsideTemp;
    private static boolean simulationRunning;
    private static boolean autoMode;
    private static boolean awayMode;
    private static AwayModeParameters awayModeParameters;

    /**
     * Default constructor for SimulationParameters object
     */
    public SimulationParameters() {
        simulationRunning = false;
        dateTime = new DateTime();
        outsideTemp = 20;
        autoMode = false;
        awayMode = false;
        awayModeParameters = new AwayModeParameters();
    }

    public boolean isAwayModeOn() {
        return awayMode;
    }

    public void setAwayMode() {
        awayMode = !awayMode;
    }

    public boolean isAutoModeOn() {
        return autoMode;
    }

    public void setAutoMode() { autoMode = !autoMode; }

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

    public AwayModeParameters getAwayModeParameters() {
        return awayModeParameters;
    }
}
