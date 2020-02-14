package com.example.Store_Digikala.model;

import java.util.List;

public class Products {

    private List<Images> images;
    private int id;
    private String name;
    private String description;
    private String price;
    private int total_sales;
    private String average_rating;
    private List<Categories> categories;


    public Products(List<Images> images, int id, String name, String description, String price, int total_sales, String average_rating, List<Categories> categories) {
        this.images = images;
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.total_sales = total_sales;
        this.average_rating = average_rating;
        this.categories = categories;
    }

    public List<Categories> getCategories() {
        return categories;
    }

    public int getTotalSales() {
        return total_sales;
    }

    public String getAverage_rating() {
        return average_rating;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Products{" +
                "images=" + images +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price='" + price + '\'' +
                ", total_sales=" + total_sales +
                ", average_rating='" + average_rating + '\'' +
                '}';
    }

    public List<Images> getImages() {
        return images;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }
}