package com.example.schedule.diary;

public class Diary {
    private int id;
    private String date;
    private String week;
    private String weather;
    private String content;

    public Diary() {
    }

    public Diary(int id, String date, String week, String weather, String content) {
        this.id = id;
        this.date = date;
        this.week = week;
        this.weather = weather;
        this.content = content;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
