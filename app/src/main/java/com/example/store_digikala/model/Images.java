package com.example.store_digikala.model;

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
