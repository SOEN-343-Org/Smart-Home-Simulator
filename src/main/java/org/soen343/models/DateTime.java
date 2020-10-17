package org.soen343.models;

import java.time.LocalDate;

public class DateTime {
    private int hours;
    private int minutes;
    private int seconds;
    private LocalDate date = LocalDate.now();

    public DateTime() {
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        if (0 <= hours && hours <= 23) {
            this.hours = hours;
        }
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        if (0 <= minutes && minutes <= 59) {
            this.minutes = minutes;
        }
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
}
