package com.example.wagba.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.wagba.database.dao.ProfileDao;
import com.example.wagba.database.entities.Profile;

@Database(entities = {Profile.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProfileDao profileDao();
}
