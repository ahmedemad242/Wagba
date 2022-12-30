package com.example.wagba.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Restaurant implements Parcelable {
    private String name;
    private String id;
    private String description;
    private String imageUrl;
    private String rating;
    private int orderCount;

    protected Restaurant(Parcel in) {
        name = in.readString();
        description = in.readString();
        imageUrl = in.readString();
        rating = in.readString();
        id = in.readString();
        orderCount = in.readInt();
    }

    public static final Creator<Restaurant> CREATOR = new Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(imageUrl);
        dest.writeString(rating);
        dest.writeString(id);
        dest.writeInt(orderCount);
    }

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }
}