package org.soen343.models.parameters;

public class SimulationParameters {
    private static DateTime dateTime;
    private static double outsideTemp;
    private static boolean simulationRunning;
    private static boolean autoMode;
    private static boolean awayMode;
    private static AwayModeParameters awayModeParameters;
    private static SmartHeatingParameters smartHeatingParameters;

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
        smartHeatingParameters = new SmartHeatingParameters();
    }

    public SmartHeatingParameters getSmartHeatingParameters() {
        return smartHeatingParameters;
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

    public void setAutoMode() {
        autoMode = !autoMode;
    }

    public boolean isSimulationRunning() {
        return simulationRunning;
    }

    public double getOutsideTemp() {
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
