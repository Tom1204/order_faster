package com.example.iut.finalproject.models;

import com.google.gson.annotations.SerializedName;

public class OrderItemRead {
    @SerializedName("id")
    private int id;
    @SerializedName("order")
    private Order order;
    @SerializedName("item")
    private Item item;
    @SerializedName("count")
    private int count;
    @SerializedName("comment")
    private String comment;
    @SerializedName("status")
    private String status;
    @SerializedName("total_price")
    private String totalPrice;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
