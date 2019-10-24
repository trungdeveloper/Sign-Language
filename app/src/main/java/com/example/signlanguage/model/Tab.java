package com.example.signlanguage.model;

public class Tab  {
    private String id;
    private String name;
    private String image;

    public Tab(String id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }
    public String getImage() {
        return image;
    }
}
