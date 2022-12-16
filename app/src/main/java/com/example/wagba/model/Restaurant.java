package com.example.wagba.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.MenuItem;

import com.example.wagba.model.Food;

import java.util.List;

public class Restaurant implements Parcelable {
    private String name;
    private String description;
    private String imageUrl;
    private List<Food> menuItems;
    private String rating;

    protected Restaurant(Parcel in) {
        name = in.readString();
        description = in.readString();
        imageUrl = in.readString();
        rating = in.readString();
        menuItems = in.readArrayList(Food.class.getClassLoader());
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
        dest.writeList(menuItems);
    }

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