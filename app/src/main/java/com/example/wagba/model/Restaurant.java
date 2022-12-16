package com.example.wagba.model;

import com.example.wagba.model.Food;

import java.util.List;

public class Restaurant {
    private String name;
    private String description;
    private String imageUrl;
    private List<Food> menuItems;
    private String rating;



    // Default constructor required for calls to DataSnapshot.getValue(Restaurant.class)
    public Restaurant() { }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<Food> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<Food> menuItems) {
        this.menuItems = menuItems;
    }
}