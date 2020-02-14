package com.example.Store_Digikala.model.orderModel;

import java.util.List;

public class OrderJsonBody {

    private Biling biling;
    private List<Order> line_items;
    private int customer_id;
    private List<Coupon> couponList;

    public OrderJsonBody(Biling biling, List<Order> line_items, int customer_id, List<Coupon> couponList) {
        this.biling = biling;
        this.line_items = line_items;
        this.customer_id = customer_id;
        this.couponList = couponList;
    }

    public OrderJsonBody(Biling biling, List<Order> line_items, int customer_id) {
        this.biling = biling;
        this.line_items = line_items;
        this.customer_id = customer_id;
    }
}
