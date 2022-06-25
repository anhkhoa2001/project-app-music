package com.example.donation50.model;

public class Donation {
    public int id;
    public int amount;
    public String method;
    public int upvotes;

    public Donation() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public Donation(int amount, String method, int upvotes) {
        this.amount = amount;
        this.method = method;
        this.upvotes = upvotes;
    }

    public Donation(int amount, String method) {
        this.amount = amount;
        this.method = method;
    }

    @Override
    public String toString() {
        return "Donation{" +
                "id=" + id +
                ", amount=" + amount +
                ", method='" + method + '\'' +
                ", upvotes=" + upvotes +
                '}';
    }
}
