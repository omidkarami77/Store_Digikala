package com.example.Store_Digikala.model;

import com.google.gson.annotations.SerializedName;

public class Images {

    @SerializedName("src")
    private String path;

    public Images(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

}