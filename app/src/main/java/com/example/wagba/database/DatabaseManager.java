package com.example.wagba.database;

import android.content.Context;

import androidx.room.Room;

public class DatabaseManager {
    private static AppDatabase sInstance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    "app_database"
            ).build();
        }
        return sInstance;
    }
}

