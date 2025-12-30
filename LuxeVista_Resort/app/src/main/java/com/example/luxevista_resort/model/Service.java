package com.example.luxevista_resort.model;

import java.io.Serializable;

public class Service implements Serializable {

    private int id;
    private String name;
    private String category;
    private String description;
    private double price;
    private int duration;
    private String imageUrl;

    public Service(int id, String name, String category, String description, double price, int duration, String imageUrl) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getDuration() {
        return duration;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
