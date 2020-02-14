package com.example.Store_Digikala.model;


public class Categories {

    private int id;
    private String name;
    private String slug;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public Categories(int id, String name, String slug) {
        this.id = id;
        this.name = name;
        this.slug = slug;
    }
}
