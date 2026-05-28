package com.example.istream;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class, PlaylistItem.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract PlaylistDao playlistDao();

    private static AppDatabase instance;

    public static AppDatabase getInstance(Context ctx) {
        if (instance == null) {
            instance = Room.databaseBuilder(ctx.getApplicationContext(),
                            AppDatabase.class, "istream_db")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}