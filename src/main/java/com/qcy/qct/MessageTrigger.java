package com.qcy.qct;

public class MessageTrigger {

    private String stringToTrigger;
    private String timerName;
    private int duration;

    public MessageTrigger(String stringToTrigger, String timerName, int duration) {
        this.stringToTrigger = stringToTrigger;
        this.timerName = timerName;
        this.duration = duration;
    }

    public String getStringToTrigger() {
        return this.stringToTrigger;
    }

    public String getTimerName() {
        return this.timerName;
    }

    public int getDuration() {
        return this.duration;
    }

}
