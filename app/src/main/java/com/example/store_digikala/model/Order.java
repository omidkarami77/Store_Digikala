package com.example.store_digikala.model;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Id;

public class Order {

    @org.greenrobot.greendao.annotation.Id
    @SerializedName("product_id")
    private int Id;
    @SerializedName("product_count")
    private int count;


    public Order(int id, int count) {
        Id = id;
        this.count = count;
    }
}
