package org.soen343.models.parameters;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class DateTime {

    private double clockSpeedMultiplier;
    private Timeline clock;
    private Calendar date;


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


    public void setTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        this.date.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY));
        this.date.set(Calendar.MINUTE, c.get(Calendar.MINUTE));
        this.date.set(Calendar.SECOND, c.get(Calendar.SECOND));
    }

    /**
     * Get Date
     *
     * @return date
     */
    public Calendar getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date.set(Calendar.YEAR, date.getYear());
        this.date.set(Calendar.MONTH, date.getMonthValue() - 1);
        this.date.set(Calendar.DAY_OF_MONTH, date.getDayOfMonth());
    }

    private void changeClockSpeed() {
        clock.setRate(clockSpeedMultiplier);
    }

    public double getClockSpeedMultiplier() {
        return clockSpeedMultiplier;
    }

    public void setClockSpeedMultiplier(double clockSpeedMultiplier) {
        this.clockSpeedMultiplier = clockSpeedMultiplier;
        changeClockSpeed();
    }
}




