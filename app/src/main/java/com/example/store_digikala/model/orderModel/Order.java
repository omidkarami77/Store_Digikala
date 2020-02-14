package com.example.Store_Digikala.model.orderModel;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Id;

public class Order {

    @Id
    @SerializedName("product_id")
    private int Id;
    @SerializedName("product_count")
    private int count;


    public Order(int id, int count) {
        Id = id;
        this.count = count;
    }
}
