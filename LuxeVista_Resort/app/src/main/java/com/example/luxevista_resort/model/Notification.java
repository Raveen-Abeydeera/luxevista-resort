package com.example.luxevista_resort.model;

public class Notification {
    private final String title;
    private final String time;
    private final boolean isHeader;


    public Notification(String title, String time) {
        this.title = title;
        this.time = time;
        this.isHeader = false;
    }


    public Notification(String title, boolean isHeader) {
        this.title = title;
        this.time = ""; // Headers don't have a time
        this.isHeader = isHeader;
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time;
    }

    public boolean isHeader() {
        return isHeader;
    }
}

