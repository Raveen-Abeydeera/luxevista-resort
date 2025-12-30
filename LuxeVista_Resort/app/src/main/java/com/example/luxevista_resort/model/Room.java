package com.example.luxevista_resort.model;

import java.io.Serializable;

public class Room implements Serializable {
    private int id;
    private String roomType;
    private String description;
    private double pricePerNight;
    private int maxAdults;
    private int maxChildren;
    private String imageUrl;

    public Room(int id, String roomType, String description, double pricePerNight, int maxAdults, int maxChildren, String imageUrl) {
        this.id = id;
        this.roomType = roomType;
        this.description = description;
        this.pricePerNight = pricePerNight;
        this.maxAdults = maxAdults;
        this.maxChildren = maxChildren;
        this.imageUrl = imageUrl;
    }

    // Getters
    public int getId() { return id; }
    public String getRoomType() { return roomType; }
    public String getDescription() { return description; }
    public double getPricePerNight() { return pricePerNight; }
    public int getMaxAdults() { return maxAdults; }
    public int getMaxChildren() { return maxChildren; }
    public String getImageUrl() { return imageUrl; }
}

