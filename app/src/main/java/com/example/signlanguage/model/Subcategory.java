package com.example.signlanguage.model;

import java.util.Comparator;

public class Subcategory {
    private String subCategory_id;
    private String id;
    private String keyword;
    private String image;
    private String video;

    public Subcategory(String subCategory_id, String id, String keyword, String image, String video) {
        this.subCategory_id = subCategory_id;
        this.id = id;
        this.keyword = keyword;
        this.image = image;
        this.video = video;
    }

    public String getSubCategory_id() {
        return subCategory_id;
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

