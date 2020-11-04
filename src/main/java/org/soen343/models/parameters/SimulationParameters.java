package org.soen343.models.parameters;

import org.soen343.models.permissions.Rule;
import org.soen343.models.permissions.SHCRule;
import org.soen343.models.permissions.Validator;

public class SimulationParameters implements Validator {
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

    public void setAutoMode() {
        if (validate() == true) {
            autoMode = !autoMode;
        }
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


    public AwayModeParameters getAwayModeParameters() {
        return awayModeParameters;
    }

    @Override
    public boolean validate() {
        Rule r = new SHCRule();
        Rule autoModeRule = r.createRule("AutoMode", 0);
        boolean isValid = autoModeRule.validate();
        if (isValid) return true;
        return false;
    }
}
