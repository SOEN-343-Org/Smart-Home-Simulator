package org.soen343.models.parameters;

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
        calendarForCallingPoliceAfterBreakIn = Calendar.getInstance();
        openLights = new ArrayList<>();
    }

    public Date getDateForCallingPoliceAfterBreakIn() {
        return calendarForCallingPoliceAfterBreakIn.getTime();
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
        Calendar c = Calendar.getInstance();
        c.setTime(lightsOpenFrom);
        AwayModeParameters.lightsOpenFrom.set(Calendar.HOUR, c.get(Calendar.HOUR));
        AwayModeParameters.lightsOpenFrom.set(Calendar.MINUTE, c.get(Calendar.MINUTE));
        AwayModeParameters.lightsOpenFrom.set(Calendar.SECOND, c.get(Calendar.SECOND));
    }

    public Date getLightsOpenTo() {
        return lightsOpenTo.getTime();
    }

    public void setLightsOpenTo(Date lightsOpenTo) {
        Calendar c = Calendar.getInstance();
        c.setTime(lightsOpenTo);
        AwayModeParameters.lightsOpenTo.set(Calendar.HOUR, c.get(Calendar.HOUR));
        AwayModeParameters.lightsOpenTo.set(Calendar.MINUTE, c.get(Calendar.MINUTE));
        AwayModeParameters.lightsOpenTo.set(Calendar.SECOND, c.get(Calendar.SECOND));
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
