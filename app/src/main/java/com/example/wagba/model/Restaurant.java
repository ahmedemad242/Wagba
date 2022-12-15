package com.example.wagba.model;

import java.util.List;

public class Restaurant {
    private String name;
    private String description;
    private String imageUrl;
    private String rating;
    public List<Food> menuItems;

    public Restaurant(String name, String description, String imageUrl, String rating, List<Food> menuItems) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.menuItems = menuItems;
    }

    public List<Food> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<Food> menuItems) {
        this.menuItems = menuItems;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getRating() {
        return rating;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRating(String description) {
        this.rating = rating;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }



}
