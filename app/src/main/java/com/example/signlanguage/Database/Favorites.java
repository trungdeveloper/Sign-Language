package com.example.signlanguage.Database;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Favorites {
    @PrimaryKey
    @NonNull
    public String id;

    @ColumnInfo(name = "keyword")
    public String keyword;

    @ColumnInfo(name = "image")
    public String image;

    @ColumnInfo(name = "video")
    public String video;


    public Favorites(String id, String keyword, String image, String video) {
        this.id = id;
        this.keyword = keyword;
        this.image = image;
        this.video = video;
    }

    public String getId() {
        return id;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getImage() {
        return image;
    }

    public String getVideo() {
        return video;
    }
}
