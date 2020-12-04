package org.soen343.models.parameters;

public class SmartHeatingParameters {

    private final int[] summerMonths = {4, 5, 6, 7, 8, 9};
    private final int[] winterMonths = {1, 2, 3, 10, 11, 12};
    private final int[] morningHours = {4, 5, 6, 7, 8, 9, 10, 11};
    private final int[] afternoonHours = {12, 13, 14, 15, 16, 17, 18, 19};
    private final int[] nightHours = {20, 21, 22, 23, 24, 1, 2, 3};

    private double summerTemp;
    private double winterTemp;

    public SmartHeatingParameters() {
        summerTemp = 20;
        winterTemp = 25;
    }

    public double getSummerTemp() {
        return summerTemp;
    }

    public void setSummerTemp(double summerTemp) {
        this.summerTemp = summerTemp;
    }

    public double getWinterTemp() {
        return winterTemp;
    }

    public void setWinterTemp(double winterTemp) {
        this.winterTemp = winterTemp;
    }

    public boolean isSummer(int currentMonth) {
        for (int m : summerMonths) {
            if (m == currentMonth)
                return true;
        }
        return false;
    }

    public String getTimePeriod(int hour){
        for (int m : morningHours) {
            if (m == hour)
                return "morning";
        }
        for (int m : afternoonHours) {
            if (m == hour)
                return "afternoon";
        }
        for (int m : nightHours) {
            if (m == hour)
                return "night";
        }
        return null;
    }
}

