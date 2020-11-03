package org.soen343.models.parameters;

import org.soen343.models.house.Light;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AwayModeParameters {
    private static int timeBeforeCallingPoliceAfterBreakIn;
    private static Calendar lightsOpenFrom;
    private static Calendar lightsOpenTo;

    private static ArrayList<Light> openLights;

    public AwayModeParameters() {
        timeBeforeCallingPoliceAfterBreakIn = 15;
        lightsOpenFrom = Calendar.getInstance();
        lightsOpenTo = Calendar.getInstance();
        openLights = new ArrayList<>();
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
        AwayModeParameters.lightsOpenFrom.setTime(lightsOpenFrom);
    }

    public Date getLightsOpenTo() {
        return lightsOpenTo.getTime();
    }

    public void setLightsOpenTo(Date lightsOpenTo) {
        AwayModeParameters.lightsOpenTo.setTime(lightsOpenTo);
    }

    public ArrayList<Light> getOpenLights() {
        return openLights;
    }

    public void setOpenLights(ArrayList<Light> lights) {
        openLights = lights;
    }

}
