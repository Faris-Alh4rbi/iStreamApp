package com.example.istream;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "playlist")
public class PlaylistItem {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String username;
    public String url;
}