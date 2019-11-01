package com.example.signlanguage.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Favorites.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FavoriteDao FavoriteDao();

}