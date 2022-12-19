package com.example.wagba.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Profile {
    @PrimaryKey
    @NonNull
    public String id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "email")
    public String email;

    public Profile(@NonNull String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

}
