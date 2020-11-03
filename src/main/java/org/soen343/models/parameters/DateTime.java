package org.soen343.models.parameters;

import javafx.animation.Animation;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

import java.time.LocalDate;
import java.util.Calendar;

public class DateTime {

    private double clockSpeedMultiplier;
    private Timeline clock;
    private Calendar date;
    private int hours = 12;
    private int minutes = 0;
    private int seconds = 0;
    private LocalDate date1 = LocalDate.now();


    public DateTime() {
        initializeClock();
        clockSpeedMultiplier = 1;
        date = Calendar.getInstance();

    }

    private void initializeClock() {
        clock = new Timeline(new KeyFrame(Duration.ZERO, e -> date.add(Calendar.SECOND, 1)),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
    }

    /**
     * Starts Clock
     */
    public void startTime() {
        clock.play();
    }

    /**
     * Stops Clock
     */
    public void stopTime() {
        clock.pause();
    }

    /**
     * Set Date
     *
     * @param calendar
     */
    public void setDate(Calendar calendar) {
        this.date = calendar;
    }

    /**
     * Get Date
     *
     * @return date
     */
    public Calendar getDate() {
        return date;
    }

    public void setClockSpeedMultiplier(double clockSpeedMultiplier) {
        this.clockSpeedMultiplier = clockSpeedMultiplier;
        changeClockSpeed();
    }

    private void changeClockSpeed() {
        clock.setRate(clockSpeedMultiplier);
    }

    public double getClockSpeedMultiplier() {
        return clockSpeedMultiplier;
    }

    /**
     * Get hours
     *
     * @return int hours
     */
    public int getHours() {
        return hours;
    }
}




