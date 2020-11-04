package org.soen343.models.parameters;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.Calendar;

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

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
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


}
