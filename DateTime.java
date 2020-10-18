package org.soen343.models;

import java.time.LocalDate;

public class DateTime {

    private int hours;
    private int minutes;
    private int seconds;
    private LocalDate date = LocalDate.now();

    public DateTime() {
    }

    /**
     *Get hours
     *
     * @return int hours
     */
    public int getHours() {
        return hours;
    }

    /**
     * Set hours
     *
     * @param hours
     */
    public void setHours(int hours) {
        if (0 <= hours && hours <= 23) {
            this.hours = hours;
        }
    }

    /**
     * Get hours
     *
     * @return int minutes
     */
    public int getMinutes() {
        return minutes;
    }

    /**
     * Set minutes
     *
     * @param minutes
     */
    public void setMinutes(int minutes) {
        if (0 <= minutes && minutes <= 59) {
            this.minutes = minutes;
        }
    }

    /**
     * Get date
     *
     * @return int date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Set date
     *
     * @param date
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Get seconds
     *
     * @return int seconds
     */
    public int getSeconds() {
        return seconds;
    }

    /**
     * Set seconds
     *
     * @param seconds
     */
    public void setSeconds(int seconds) {
        if (0 <= seconds && seconds <= 59) {
            this.seconds = seconds;
        }
    }
}
