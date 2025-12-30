package com.example.luxevista_resort.model;

import java.io.Serializable;

public class Booking implements Serializable {
    private int id;
    private int guestId;
    private String bookingType;
    private int itemId;
    private String date;
    private String details;
    private String itemName;
    private String imageUrl;

    public Booking(int id, int guestId, String bookingType, int itemId, String date, String details, String itemName, String imageUrl) {
        this.id = id;
        this.guestId = guestId;
        this.bookingType = bookingType;
        this.itemId = itemId;
        this.date = date;
        this.details = details;
        this.itemName = itemName;
        this.imageUrl = imageUrl;
    }

    // Getters
    public int getId() { return id; }
    public int getGuestId() { return guestId; }
    public String getBookingType() { return bookingType; }
    public int getItemId() { return itemId; }

    public String getDate() { return date; }

    public String getDetails() { return details; }
    public String getItemName() { return itemName; }
    public String getImageUrl() { return imageUrl; }
}
