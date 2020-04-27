package com.example.schedule.data;

import java.util.Date;

public class Event implements Comparable {
    private int eventId;
    private boolean isDone;
    private String eventTitle;
    private String eventPs;
    private Date dateLine;
    private int labelId;

    public Event(int eventId, boolean isDone, String eventTitle, String eventPs, Date dateLine, int labelId) {
        this.eventId = eventId;
        this.isDone = isDone;
        this.eventTitle = eventTitle;
        this.eventPs = eventPs;
        this.dateLine = dateLine;
        this.labelId = labelId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventPs() {
        return eventPs;
    }

    public void setEventPs(String eventPs) {
        this.eventPs = eventPs;
    }

    public Date getDateLine() {
        return dateLine;
    }

    public void setDateLine(Date dateLine) {
        this.dateLine = dateLine;
    }

    public int getLabelId() {
        return labelId;
    }

    public void setLabelId(int labelId) {
        this.labelId = labelId;
    }

    @Override
    public int compareTo(Object o) {
        return dateLine.compareTo(((Event) o).getDateLine());
    }
}
