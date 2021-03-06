package com.example.Store_Digikala.model;

public class Review {
    private String review;
    private int id ;
    private String date_created;
    private int product_id;
    private String status ;
    private String reviewer;
    private String reviewer_email;

    public Review(String review, int id, String date_created, int product_id, String status, String reviewer, String reviewer_email) {
        this.review = review;
        this.id = id;
        this.date_created = date_created;
        this.product_id = product_id;
        this.status = status;
        this.reviewer = reviewer;
        this.reviewer_email = reviewer_email;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public String getReviewer_email() {
        return reviewer_email;
    }

    public void setReviewer_email(String reviewer_email) {
        this.reviewer_email = reviewer_email;
    }
}
