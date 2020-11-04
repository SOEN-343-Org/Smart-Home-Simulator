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

    public AwayModeParameters() {
        timeBeforeCallingPoliceAfterBreakIn = 15;
        lightsOpenFrom = Calendar.getInstance();
        lightsOpenTo = Calendar.getInstance();
        calendarForCallingPoliceAfterBreakIn = null;
        openLights = new ArrayList<>();
    }

    public Date getDateForCallingPoliceAfterBreakIn() {
        if (calendarForCallingPoliceAfterBreakIn != null)
            return calendarForCallingPoliceAfterBreakIn.getTime();
        return null;
    }

    public int getTimeBeforeCallingPoliceAfterBreakIn() {
        return timeBeforeCallingPoliceAfterBreakIn;
    }

    public void setTimeBeforeCallingPoliceAfterBreakIn(int timeBeforeCallingPoliceAfterBreakIn) {
        AwayModeParameters.timeBeforeCallingPoliceAfterBreakIn = timeBeforeCallingPoliceAfterBreakIn;
    }

    public Date getLightsOpenFrom() {
        return lightsOpenFrom.getTime();
    }

    public void setLightsOpenFrom(Date lightsOpenFrom) {
        Calendar c = (Calendar) Model.getSimulationParameters().getDateTime().getDate().clone();
        AwayModeParameters.lightsOpenFrom.setTime(lightsOpenFrom);
        AwayModeParameters.lightsOpenFrom.set(Calendar.YEAR, c.get(Calendar.YEAR));
        AwayModeParameters.lightsOpenFrom.set(Calendar.MONTH, c.get(Calendar.MONTH));
        AwayModeParameters.lightsOpenFrom.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH));
    }

    public Date getLightsOpenTo() {
        return lightsOpenTo.getTime();
    }

    public void setLightsOpenTo(Date lightsOpenTo) {
        Calendar c = (Calendar) Model.getSimulationParameters().getDateTime().getDate().clone();
        AwayModeParameters.lightsOpenTo.setTime(lightsOpenTo);
        AwayModeParameters.lightsOpenTo.set(Calendar.YEAR, c.get(Calendar.YEAR));
        AwayModeParameters.lightsOpenTo.set(Calendar.MONTH, c.get(Calendar.MONTH));
        AwayModeParameters.lightsOpenTo.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH));
    }

    public ArrayList<Light> getOpenLights() {
        return openLights;
    }

    public void setOpenLights(ArrayList<Light> lights) {
        openLights = lights;
    }

    public void setDateBeforeCallingPolice(Calendar calendar) {
        calendarForCallingPoliceAfterBreakIn = calendar;
    }
}
