package com.example.wagba.model;

public class Restaurant {
    private String name;
    private String description;
    private Integer imageUrl;
    private String rating;

    public Restaurant(String name, String description, Integer imageUrl, String rating) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.rating = rating;
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

    public Integer getImageUrl() {
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

    public void setImageUrl(Integer imageUrl) {
        this.imageUrl = imageUrl;
    }



}
