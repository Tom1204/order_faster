package com.example.iut.finalproject.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    private int id, tableNumber;
    private Date createdDateTime;
    private Double totalPrice;
    private String status;
    private List<Item> orderedFood;

    public Order(int id, int tableNumber, Date createdDateTime, Double totalPrice) {
        this.id = id;
        this.tableNumber = tableNumber;
        this.createdDateTime = createdDateTime;
        this.totalPrice = totalPrice;
        this.orderedFood = new ArrayList<>();
    }

    public Order(int id, Date createdDateTime, Double totalPrice) {
        this.id = id;
        this.createdDateTime = createdDateTime;
        this.totalPrice = totalPrice;
        this.orderedFood = new ArrayList<>();
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

    public void addFood(Item food) {
        this.orderedFood.add(food);
    }
}
