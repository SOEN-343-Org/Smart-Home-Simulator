package org.soen343.models.parameters;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class DateTime {

    private Calendar calendar;
    private Timeline time;

    public DateTime() {
        calendar = Calendar.getInstance();
        initializeTime();
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendarTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        calendar.set(Calendar.HOUR, c.get(Calendar.HOUR));
        calendar.set(Calendar.MINUTE, c.get(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, c.get(Calendar.SECOND));
    }

    public Timeline getTime() {
        return time;
    }

    public void setTime(Timeline time) {
        this.time = time;
    }

    public void startTime() {
        time.play();
    }

    public void stopTime() {
        time.pause();
    }


    private void initializeTime() {
        time = new Timeline(new KeyFrame(Duration.ZERO, e -> calendar.add(Calendar.SECOND, 1)),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
    }


    public void setCalendarDate(LocalDate date) {
        calendar.set(Calendar.YEAR, date.getYear());
        calendar.set(Calendar.MONTH, date.getMonthValue());
        calendar.set(Calendar.DAY_OF_MONTH, date.getDayOfMonth());
    }
}
