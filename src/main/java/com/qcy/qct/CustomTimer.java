package com.qcy.qct;

import java.util.Date;

// Timer object for keeping track of all timers currently active to be displayed on the HUD
public class CustomTimer {

    private String timerName;
    private Date startTime;
    private int duration; // In seconds

    public CustomTimer(String timerName, Date startTime, int duration) {
        this.timerName = timerName;
        this.startTime = startTime;
        this.duration = duration;
    }

    public String getTimerName() {
        return this.timerName;
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public int getDuration() {
        return this.duration;
    }
}
