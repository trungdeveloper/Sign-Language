package com.example.signlanguage.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavoriteDao {
    @Query("SELECT * FROM Favorites")
    List<Favorites> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Favorites... favorites);

    @Delete
    void delete(Favorites favorites);


    @Query("DELETE FROM Favorites")
    void deleteAll();


}
