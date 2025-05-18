package com.example.entity;

import java.io.Serializable;

public class Property implements Serializable {
    private String id;
    private String title;
    private String description;
    private double price;
    private String status;
    private String ownerId;
    private String imageUrl;

    public Property() {}

    public Property(String id, String title, String description, double price, String status, String ownerId, String imageUrl) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.status = status;
        this.ownerId = ownerId;
        this.imageUrl = imageUrl;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getOwnerId() { return ownerId; }
    public void setOwnerId(String ownerId) { this.ownerId = ownerId; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
} 