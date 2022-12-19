package com.example.wagba.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.wagba.database.entities.Profile;


@Dao
public interface ProfileDao {
    @Insert
    void insert(Profile profile);

    @Query("SELECT * FROM profile WHERE id = :id")
    Profile getById(String id);

    @Update
    void update(Profile profile);

    @Delete
    void delete(Profile profile);

}

