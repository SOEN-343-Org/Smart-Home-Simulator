package org.soen343.models.parameters;

import org.soen343.models.Model;
import org.soen343.models.house.Light;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AwayModeParameters {
    private static int timeBeforeCallingPoliceAfterBreakIn;
    private static Calendar calendarForCallingPoliceAfterBreakIn;
    private static Calendar lightsOpenFrom;
    private static Calendar lightsOpenTo;

    private static ArrayList<Light> openLights;

    /**
     * Constructor for the parameters of away mode
     */
    public AwayModeParameters() {
        timeBeforeCallingPoliceAfterBreakIn = 15;
        lightsOpenFrom = Calendar.getInstance();
        lightsOpenTo = Calendar.getInstance();
        calendarForCallingPoliceAfterBreakIn = null;
        openLights = new ArrayList<>();
    }

    /**
     * Returns the date when the police need to be called
     * @return Date or null if Calendar is null
     */
    public Date getDateForCallingPoliceAfterBreakIn() {
        if (calendarForCallingPoliceAfterBreakIn != null)
            return calendarForCallingPoliceAfterBreakIn.getTime();
        return null;
    }

    /**
     * Returns the time before calling the police
     * @return int
     */
    public int getTimeBeforeCallingPoliceAfterBreakIn() {
        return timeBeforeCallingPoliceAfterBreakIn;
    }

    /**
     * Sets the time before calling the break-in
     * @param timeBeforeCallingPoliceAfterBreakIn int time
     */
    public void setTimeBeforeCallingPoliceAfterBreakIn(int timeBeforeCallingPoliceAfterBreakIn) {
        AwayModeParameters.timeBeforeCallingPoliceAfterBreakIn = timeBeforeCallingPoliceAfterBreakIn;
    }

    /**
     * Gets the From date
     * @return from date
     */
    public Date getLightsOpenFrom() {
        return lightsOpenFrom.getTime();
    }

    /**
     * Sets the From date
     * @param lightsOpenFrom Date from
     */
    public void setLightsOpenFrom(Date lightsOpenFrom) {
        Calendar c = (Calendar) Model.getSimulationParameters().getDateTime().getDate().clone();
        AwayModeParameters.lightsOpenFrom.setTime(lightsOpenFrom);
        AwayModeParameters.lightsOpenFrom.set(Calendar.YEAR, c.get(Calendar.YEAR));
        AwayModeParameters.lightsOpenFrom.set(Calendar.MONTH, c.get(Calendar.MONTH));
        AwayModeParameters.lightsOpenFrom.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * Gets the To date
     * @return to date
     */
    public Date getLightsOpenTo() {
        return lightsOpenTo.getTime();
    }

    /**
     * Sets the to date
     * @param lightsOpenTo Date to
     */
    public void setLightsOpenTo(Date lightsOpenTo) {
        Calendar c = (Calendar) Model.getSimulationParameters().getDateTime().getDate().clone();
        AwayModeParameters.lightsOpenTo.setTime(lightsOpenTo);
        AwayModeParameters.lightsOpenTo.set(Calendar.YEAR, c.get(Calendar.YEAR));
        AwayModeParameters.lightsOpenTo.set(Calendar.MONTH, c.get(Calendar.MONTH));
        AwayModeParameters.lightsOpenTo.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * Gets the lights that need to be on during away mode
     * @return List of lights
     */
    public ArrayList<Light> getOpenLights() {
        return openLights;
    }

    /**
     * Sets the lights that need to be on during away mode
     * @param lights list of lights
     */
    public void setOpenLights(ArrayList<Light> lights) {
        openLights = lights;
    }

    /**
     * Set calendar before calling the police
     * @param calendar Calendar
     */
    public void setDateBeforeCallingPolice(Calendar calendar) {
        calendarForCallingPoliceAfterBreakIn = calendar;
    }
}
