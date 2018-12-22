package com.example.iut.finalproject.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ManagerOrder implements Serializable {
    public static final String  REJECTED = "rejected";
    public static final String  PENDING = "pending";
    public static final String  FINISHED = "finished";

    @SerializedName("id")
    private int id;
    @SerializedName("user")
    private Client user;
    @SerializedName("table")
    private int tableNumber;
    @SerializedName("created_date")
    private Date createdDateTime;
    @SerializedName("total_price")
    private Double totalPrice;
    @SerializedName("status")
    private String status;
    @SerializedName("order_items")
    private ManagerOrderItemList orderItems;

    public ManagerOrder(int id, int tableNumber, Date createdDateTime, Double totalPrice) {
        this.id = id;
        this.tableNumber = tableNumber;
        this.createdDateTime = createdDateTime;
        this.totalPrice = totalPrice;
        this.orderItems = new ManagerOrderItemList();
    }

    public ManagerOrderItemList getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(ManagerOrderItemList orderItems) {
        this.orderItems = orderItems;
    }

    public ManagerOrder(int tableNumber, ManagerOrderItemList orderItems) {
        this.tableNumber = tableNumber;
        this.orderItems = orderItems;
    }

    public ManagerOrder(int id, Date createdDateTime, Double totalPrice) {
        this.id = id;
        this.createdDateTime = createdDateTime;
        this.totalPrice = totalPrice;
        this.orderItems = new ManagerOrderItemList();
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

    public void addFood(ManagerOrderItem orderItem) {
        this.orderItems.add(orderItem);
    }

    public Client getUser() {
        return user;
    }

    public void setUser(Client user) {
        this.user = user;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
