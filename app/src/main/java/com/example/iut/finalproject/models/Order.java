package com.example.iut.finalproject.models;

import com.example.iut.finalproject.Database.Model.OrderItem;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    @SerializedName("id")
    private int id;
    @SerializedName("table")
    private int tableNumber;
    @SerializedName("created_date")
    private Date createdDateTime;
    @SerializedName("total_price")
    private Double totalPrice;
    @SerializedName("status")
    private String status;
    @SerializedName("order_items")
    private List<OrderItem> orderItem;

    public Order(int id, int tableNumber, Date createdDateTime, Double totalPrice) {
        this.id = id;
        this.tableNumber = tableNumber;
        this.createdDateTime = createdDateTime;
        this.totalPrice = totalPrice;
        this.orderItem = new ArrayList<>();
    }

    public Order(int tableNumber, List<OrderItem> orderItem) {
        this.tableNumber = tableNumber;
        this.orderItem = orderItem;
    }

    public Order(int id, Date createdDateTime, Double totalPrice) {
        this.id = id;
        this.createdDateTime = createdDateTime;
        this.totalPrice = totalPrice;
        this.orderItem = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public Date getCreatedDateTime() {
        return createdDateTime;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void addFood(OrderItem orderItem) {
        this.orderItem.add(orderItem);
    }
}
