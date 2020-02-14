package com.example.Store_Digikala.model;

import java.util.List;

public class Attribute {
    private String name;
    private List<String> productOption;
    private int id;

    public Attribute(String name, List<String> productOption, int id) {
        this.name = name;
        this.productOption = productOption;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getProductOption() {
        return productOption;
    }

    public void setProductOption(List<String> productOption) {
        this.productOption = productOption;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
